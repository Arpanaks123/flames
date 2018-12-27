package com.scaleup.flames.Service.Impl;

import com.scaleup.flames.Service.RegisterService;
import com.scaleup.flames.domain.User;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Override
    public User userWithEmail(String email) {
        return null;
    }

    @Override
    public User userWithMobile(String mobile) {
        return null;
    }

    @Override
    public String registerAccount(String mobile, String referrerId, String accessType, String region) {
        return null;
    }
}
