package avi.service;


import avi.constants.AppConstants;
import avi.dto.response.AppResponse;
import avi.utils.AppHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class AppService {



    @Autowired
    private AppHttpClient appHttpClient;

    @Autowired
    private PartitionAndExecutionService tService;



    public List<AppResponse> refreshCache(List<String> urlList) throws ExecutionException, InterruptedException {

        List<Future<AppResponse>> futureTaskList = new ArrayList<>();
        for (String url : urlList) {
            Future<AppResponse> execute = tService.executeCallable(() ->
             {  AppResponse done = null;
                try {
                    done =  callOnce(url);
                } catch (Exception e) {
                   done = new AppResponse<>(AppConstants.CATCH_BLOCK_EXCEPTION,"Callable Task",e.getMessage());
                }
                return done; });

            futureTaskList.add(execute);
        }

        List<AppResponse> allDone = tService.isAllDone(futureTaskList);
        return allDone;

    }



    public AppResponse callOnce(String targetUrl){

        AppResponse appResponse = appHttpClient.makeGetCall(targetUrl, null);

        return appResponse;
    }

    public AppResponse callTillSuccessful(String targetUrl){

        while (appHttpClient.makeGetCall(targetUrl, null).getStatusCode() != 200){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new AppResponse(200, "success");
    }






}
