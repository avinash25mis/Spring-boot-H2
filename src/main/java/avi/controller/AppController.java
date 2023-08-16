package avi.controller;



import avi.constants.ConfigConstant;
import avi.dao.AppRepository;
import avi.dto.UrlDetails;
import avi.dto.response.AppResponse;
import avi.service.AppService;
import avi.utils.AppHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
@CrossOrigin("*")
@RequestMapping(value = {"/app","/",""})
public  class AppController {

@Autowired
private AppRepository repository;

@Autowired
private AppService appService;

@Autowired
private AppHttpClient appHttpClient;



    @RequestMapping(value = {"/home","/",""})
    public String home(Map map) {
        map.put("connectionRequestTimeout", ConfigConstant.connectionRequestTimeout);
        map.put("connectTimeout", ConfigConstant.connectTimeout);
        map.put("threadPoolSize", ConfigConstant.threadPoolSize);
        map.put("numOfCall", ConfigConstant.numOfCall);
        return "app";
    }


    @RequestMapping("/getUrlList")
    @ResponseBody
    public AppResponse getUrls(@RequestParam(name = "urls",required = false) String urls) throws InterruptedException, ExecutionException {
         if(StringUtils.isEmpty(urls) || urls.trim().length() == 0 || Arrays.asList(urls.split("\\r?\\n")).size()<1) {
            return new AppResponse(500,"urls is empty");
         }
         List<String> urlList = Arrays.asList(urls.split("\\r?\\n"));

         return new AppResponse(200,  getListFromString(urlList));

    }



    @RequestMapping("/makeCallAndRefresh")
    @ResponseBody
    public AppResponse makeCallAndRefresh(@RequestParam(name = "urls",required = false) String urls) throws InterruptedException, ExecutionException {
        if(StringUtils.isEmpty(urls)) {
            return new AppResponse(500,"url is empty");
        }
        List<String> urlList = Arrays.asList(urls.split("\\r?\\n"));
        List<AppResponse> responses = appService.refreshCache(getValidUrls(urlList));
       AppResponse response = new AppResponse(200,getList(responses));
       return response;

    }

    private static  List<String> getValidUrls(List<String> urlList) {
        List<String> validUrl= new ArrayList<>();
        for(String uRL: urlList){
           if(uRL.contains("http")){
               validUrl.add(uRL.trim());
            }
        }
        return validUrl;
    }


    List<UrlDetails> getListFromString(List<String> urlList){
        List<UrlDetails> list = new ArrayList<>();
        for(int i =0;i<urlList.size();i++){
            UrlDetails dto= new UrlDetails(i+1,urlList.get(i),false);
            list.add(dto);
        }
        return list;
    }

    List<UrlDetails> getList( List<AppResponse> responses){
        List<UrlDetails> list = new ArrayList<>();
        for(int i =0;i<responses.size();i++){
            UrlDetails dto= new UrlDetails(i+1,responses.get(i).getMessage(),(Boolean) responses.get(i).getResult());
            list.add(dto);
        }
        return list;
    }




}
