package avi.controller;

import avi.dao.CommonRepository;
import avi.dto.response.GenericResponse;
import avi.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ResourceBundle;

/**
 * @author avinash.a.mishra
 */

@RestController
@RequestMapping("/User")
public class UserController {

   @Autowired
   private CommonRepository commonRepository;

@PostMapping("/register")
public GenericResponse registerUser(@Valid @RequestBody AppUser appUser) {
    commonRepository.saveOrUpdate(appUser);
    ResourceBundle bundle=ResourceBundle.getBundle("messages/messages");
    String message = bundle.getString("app.success");
    return new GenericResponse(message,"User Registered",null);
  }



  @PostMapping("/update")
    public GenericResponse updateUser(@RequestBody AppUser appUser){

      return new GenericResponse("success","User Registered",null);
  }
}
