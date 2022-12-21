package avi;



import avi.dao.CommonRepository;
import avi.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/Common")
public  class   CommonController {

@Autowired
private CommonRepository repository;

@Autowired
private CommonService commonService;

    @GetMapping("/home")
    public String home(HttpRequest httpRequest)
    {

        return "home";
    }








}
