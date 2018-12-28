package com.scaleup.flames.Service;

import com.scaleup.flames.domain.User;

public interface RegisterService {


    User userWithEmail(String email);

    User userWithMobile(String mobile);

    String registerAccount(User user);
}
