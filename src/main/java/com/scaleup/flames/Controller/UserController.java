package com.scaleup.flames.Controller;

import com.scaleup.flames.Service.UserService;
import com.scaleup.flames.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserController {

    @Autowired
    private UserService loginService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ResponseBody
    @RequestMapping(value = "/user/login", method = POST)
    public ResponseEntity<?> registerwithMobile(@RequestBody(required = true) User user, HttpServletResponse response){

        loginService.userDetails(user, response);
        return null;
    }

}
