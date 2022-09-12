package com.bridgelabz.fundoouserservice.controller;

import com.bridgelabz.fundoouserservice.dto.UserServiceDTO;
import com.bridgelabz.fundoouserservice.model.UserServiceModel;
import com.bridgelabz.fundoouserservice.service.IUserService;
import com.bridgelabz.fundoouserservice.util.Response;
import com.bridgelabz.fundoouserservice.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/UserService")
public class UserServiceController {

    @Autowired
    IUserService userService;

    @GetMapping("/welcome")
    public String welcomeMessage() {

        return "Welcome to fundoo user project";
    }

    @PostMapping("/addUser")
    public ResponseEntity<Response> addUser(@Valid @RequestBody UserServiceDTO userServiceDTO) {
        Response response = userService.addUser(userServiceDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUtil> login(@RequestParam String emailId, @RequestParam String password) {
        ResponseUtil response = userService.login(emailId, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<Response> updateUser(@Valid @RequestBody UserServiceDTO userServiceDTO,@PathVariable Long id, @RequestHeader String token) {
        Response response = userService.updateUser(userServiceDTO, id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<List> getUsers(@RequestHeader String token) {
        List<UserServiceModel> responseUtil = userService.getUsers(token);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Response> deleteUser(@RequestHeader String token, @RequestParam Long id) {
        Response response = userService.deleteUser(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<Response> getUser(@RequestParam Long id, @RequestHeader String token) {
        Response response = userService.getUser(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Response> updatePassword(@RequestHeader String token,
                                                   @RequestParam String password) {
        Response response = userService.updatePassword(token, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword(@RequestParam String emailId) {
        Response response = userService.resetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public Boolean validate(@PathVariable String token) {
        return userService.validate(token);
    }

    @PutMapping("/restoreUser")
    public ResponseEntity<Response> restoreUser(@RequestParam Long id, @RequestHeader String token) {
        Response response = userService.restoreUser(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUsers")
    public ResponseEntity<Response> deleteUsers(@RequestParam Long id, @RequestHeader String token) {
        Response response = userService.deleteUsers(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/deletePermanent")
    public ResponseEntity<Response> deletePermanent(@RequestParam Long id,@RequestHeader String token) {
        Response response = userService.deletePermanent(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}




