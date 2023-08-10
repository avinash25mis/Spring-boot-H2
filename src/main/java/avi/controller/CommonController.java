package avi.controller;



import avi.dao.CommonRepository;
import avi.dto.response.GenericResponse;
import avi.service.CommonService;
import avi.utils.AppHttpClient;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.beans.JavaBean;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("*")
@RequestMapping("/Common")
public  class   CommonController {

@Autowired
private CommonRepository repository;


@Autowired
RestTemplate restTemplate;

@Autowired
private CommonService commonService;

@Autowired
private AppHttpClient appHttpClient;






    @RequestMapping("/test")
    @ResponseBody
    public String test() throws InterruptedException
    {
        String token="-2368570946505741478GUSPR138504650";
        Map<String,String> headers = new HashMap<>();
        headers.put("Cookie","WCW_Authentication="+token);
        String targetUtl = "https://api.pdoqa.aws.gartner.com/user/profile";
        GenericResponse response = appHttpClient.getCall(targetUtl, headers);

        return response.toString();
    }

    @RequestMapping("/home")
    public String home() throws InterruptedException {
      //Thread.sleep(6000);
        return "home";
    }




}
