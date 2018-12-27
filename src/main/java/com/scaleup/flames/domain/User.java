package com.scaleup.flames.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String password;
    private boolean active;
    private boolean verified;
    private String userId;
    private String accessType;
    private String region;
    private String language;
    private String city;
    private String country;
    private String mobileNumber;
    private Date birthDate;
}
