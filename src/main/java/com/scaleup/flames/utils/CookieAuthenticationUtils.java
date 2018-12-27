package com.scaleup.flames.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieAuthenticationUtils {

    public static final String AUTH_COOKIE_NAME = "token";
    public static final int EXPIRY_TIME = 60*60*24*365*2;
    public static final String USER_COOKIE_NAME = "user";
    
    public static final String SPEC_TOKEN_NAME = "spectoken";

    public static void addAuthenticationCookie(HttpServletResponse response, String id) {
        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, id);
        cookie.setPath("/");
        cookie.setMaxAge(EXPIRY_TIME);
        response.addCookie(cookie);
        
        cookie = new Cookie(SPEC_TOKEN_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);     
    }

    public static void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        
        cookie = new Cookie(USER_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);                
    }
}
