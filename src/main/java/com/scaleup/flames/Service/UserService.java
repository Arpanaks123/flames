package com.scaleup.flames.Service;

import com.scaleup.flames.domain.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    List<String> loginUser(String userName, String password);

    User userDetails(User user, HttpServletResponse response);
}
