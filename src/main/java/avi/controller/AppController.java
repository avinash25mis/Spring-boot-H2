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
        AppResponse response = null;
        if (StringUtils.isEmpty(url)) {
            response = new AppResponse(500, "url is empty");
        } else {
            List<String> urlList = Arrays.asList(url.split("\\r?\\n"));
            ;
            response = new AppResponse(200, urlList);
        }
        return response;
    }


    @RequestMapping("/refreshCache")
    @ResponseBody
    public AppResponse refreshCache(@RequestParam(name = "url") String url) throws InterruptedException, ExecutionException {
        AppResponse response = null;
        if(StringUtils.isEmpty(url)) {
            response = new AppResponse(500,"url is empty");
        }else{
           List<String> urlList = Arrays.asList(url.split("\\r?\\n"));
            List<AppResponse> responses = appService.refreshCache(urlList);
            response = new AppResponse(200,responses);
        }
     return response;

    }




}
