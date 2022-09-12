package com.bridgelabz.fundoouserservice.model;


import com.bridgelabz.fundoouserservice.dto.UserServiceDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/*
 * Purpose : UserModel are used to transfer the data into database
*/

@Data
@Entity
@Table(name = "user")
public class UserServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String emailId;
    private Date DOB;
    private String phoneNumber;
    private String profilePic;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private String password;
    private boolean active;
    private boolean deleted;



    public UserServiceModel(UserServiceDTO userServiceDTO) {
        this.name = userServiceDTO.getName();
        this.emailId = userServiceDTO.getEmailId();
        this.password = userServiceDTO.getPassword();
        this.DOB = userServiceDTO.getDOB();
        this.phoneNumber = userServiceDTO.getPhoneNumber();
        this.profilePic = userServiceDTO.getProfilePic();
    }

    public UserServiceModel() {

    }
}
