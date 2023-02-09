package avi;



import avi.dao.CommonRepository;
import avi.service.CommonService;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.beans.JavaBean;
import java.net.http.HttpRequest;

@RestController
@CrossOrigin("*")
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

    @GetMapping("/home2")
    public String home()
    {

        return "home";
    }










}
