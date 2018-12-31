package com.scaleup.flames.Controller;

import com.scaleup.flames.Controller.domain.AppErrorMessage;
import com.scaleup.flames.Service.RegisterService;
import com.scaleup.flames.Service.exception.UserAlreadyRegisteredException;
import com.scaleup.flames.domain.User;
import com.scaleup.flames.utils.CookieAuthenticationUtils;
import com.scaleup.flames.utils.RegionUtils;
import com.scaleup.flames.utils.ResponseEntityCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class RegisterController {

    String name;
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    RegisterService registerService;

    @RequestMapping(value = "/user/registration/{email}", method = POST)
    public ResponseEntity<?> registerWithEmail(@PathVariable String email) throws IllegalAccessException {

        Object existing = registerService.userWithEmail(email);
        if (existing != null)
            throw new IllegalAccessException("User already exists!!");
        try {
            registerService.registerAccount(email);
            return ResponseEntityCreator.success();
        } catch (Exception e) {
            logger.info("Error occurred while registration..", e.getMessage());
        }
        return null;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseBody
    @RequestMapping(value = "/user/profile/{uuid}", method = POST)
    public ResponseEntity<?> registerwithMobile(@PathVariable String uuid, @RequestBody(required = true) User user){

        registerService.editProfile(uuid, user);
        return null;
    }
}
