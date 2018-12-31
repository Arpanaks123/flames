package com.scaleup.flames.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.boot.autoconfigure.security.*;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/user/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/**").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login-error");

        http
                .formLogin()
                .loginPage(LOGIN_URL)
                .failureHandler(authenticationFailureHandler())
                .usernameParameter("user")
                .passwordParameter("password")
                .defaultSuccessUrl("/user/success").permitAll();
        http
                .exceptionHandling()
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint(LOGIN_URL));

        http
                .logout()
                .invalidateHttpSession(true)
                .permitAll()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception)
                ->
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Username or password invalid.");
    }
}