package com.scaleup.flames.Service;

import com.scaleup.flames.domain.User;

public interface RegisterService {


    Object userWithEmail(String email);

    User userWithMobile(String mobile);

    void registerAccount(String email);

    User editProfile(String uuid, User user);
}
