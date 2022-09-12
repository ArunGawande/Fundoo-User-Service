package com.bridgelabz.fundoouserservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/*
 * Purpose : UserDTO are used to create and update fundoo user
 * */

@Data
public class UserServiceDTO {

    @Pattern(regexp = "[A-Z][a-z]{2,}", message = "Invalid name")
    private String name;

    @Pattern(regexp = "[a-z][A-Z a-z 0-9]+[@][a-z]+[.][a-z]{2,}", message = "Invalid email id")
    private String emailId;

     @Pattern(regexp = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}", message = "Invalid password")
    private String password;

    private Date DOB;

    @Pattern(regexp = "[+]91 [6-9]\\d{9}", message = "Invalid phone number")
    private String phoneNumber;
    private String profilePic;

}
