package avi.controller;



import avi.dao.AppRepository;
import avi.dto.response.AppResponse;
import avi.service.AppService;
import avi.utils.AppHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@CrossOrigin("*")
@RequestMapping("/Common")
public  class AppController {

@Autowired
private AppRepository repository;

@Autowired
private AppService appService;

@Autowired
private AppHttpClient appHttpClient;



    @RequestMapping("/home")
    public String home() throws InterruptedException {
        //Thread.sleep(6000);
        return "home";
    }


    @RequestMapping("/getUrls")
    @ResponseBody
    public AppResponse getUrls(@RequestParam(name = "url") String url) throws InterruptedException, ExecutionException {
         if(StringUtils.isEmpty(url)) {
            return new AppResponse(500,"url is empty");
         }
         List<String> urlList = Arrays.asList(url.split("\\r?\\n"));
         return new AppResponse(200, urlList);

    }


    @RequestMapping("/refreshCache")
    @ResponseBody
    public AppResponse refreshCache(@RequestParam(name = "url") String url) throws InterruptedException, ExecutionException {
        if(StringUtils.isEmpty(url)) {
            return new AppResponse(500,"url is empty");
        }

       List<String> urlList = Arrays.asList(url.split("\\r?\\n"));
       List<AppResponse> responses = appService.refreshCache(urlList);
       AppResponse response = new AppResponse(200,responses);
       return response;

    }




}
