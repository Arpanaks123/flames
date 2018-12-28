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

    @ResponseBody
    @RequestMapping(value = "/user/registration/{email}", method = POST)
    public ResponseEntity<?> registerWithEmail(@PathVariable String email) throws IllegalAccessException {

        Object existing = registerService.userWithEmail(email);
        if (existing != null)
            throw new IllegalAccessException("User already exists!!");
        try {
            registerService.registerAccount(email);
            return ResponseEntityCreator.success();
        } catch(Exception e){
            logger.info("Error occurred while registration..", e.getMessage());
        }
            return null;
    }

    @ResponseBody
    @RequestMapping(value = "/user/registration/{mobile}/", method = GET)
    public ResponseEntity<?> registerwithMobile(@PathVariable String mobile,
                                                @RequestBody(required = true) User user, HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {

        User existing = registerService.userWithMobile(mobile);
        if (existing != null)
            return ResponseEntityCreator.error(response, AppErrorMessage.USER_ALREADY_REGISTERED, "mobile");

        try {

            String region = (String) request.getAttribute(RegionUtils.REGION_PARAM_NAME);
            //registerService.registerAccount(user);

            CookieAuthenticationUtils.addAuthenticationCookie(response, null);

            return ResponseEntityCreator.success();

        } catch (UserAlreadyRegisteredException e) {
            return ResponseEntityCreator.error(response, AppErrorMessage.USER_ALREADY_REGISTERED, "mobile");
        }
    }

}
