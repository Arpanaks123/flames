package com.scaleup.flames.Service.Impl;

import com.scaleup.flames.Service.UserService;
import com.scaleup.flames.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private RestTemplate rest;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    static List<String> cookies = null;
    @Override
    public List<String> loginUser(String username, String password) {
        //LOG.info("Absolute URL is '{}'", conf.getIatAdminLoginResource());

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("user", username);
        parameters.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parameters,
                headers);
        ResponseEntity<String> resEntity = rest.postForEntity("http://localhost:8080/user/login", entity, String.class);
        System.out.println(resEntity);
        if (resEntity != null && resEntity.getStatusCode() == HttpStatus.FOUND) {
            cookies = resEntity.getHeaders().get("Set-Cookie");
            LOG.info("Login Response is '{}'" + resEntity.getHeaders().get("Set-Cookie"));
        }
        return cookies;

    }

    @Override
    public User userDetails(User user, HttpServletResponse response) {
        return null;
    }
}
