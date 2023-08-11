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
                    done =  callMultiple(url);
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
        AppResponse appResponse = appHttpClient.makeGetCall(targetUrl, null,1);
        return appResponse;
    }

    public AppResponse callMultiple(String targetUrl) throws InterruptedException {
       int totalCalls = 0;
       boolean gotSuccess = false;
        for(int i=1;i<4;i++){
            AppResponse appResponse = appHttpClient.makeGetCall(targetUrl, null,i);
            totalCalls = i;
            if(appResponse.getStatusCode() == 200) {
                gotSuccess =false;
                 break;
             }else {
                Thread.sleep(2000);
             }
        }
        return new AppResponse(200,"totall Calls ="+totalCalls, targetUrl,"success="+gotSuccess);
        }

    }







