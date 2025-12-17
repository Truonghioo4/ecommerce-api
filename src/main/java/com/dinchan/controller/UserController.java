package com.dinchan.controller;

import com.dinchan.model.Address;
import com.dinchan.model.User;
import com.dinchan.response.AuthResponse;
import com.dinchan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/users/profile")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/users/profile")
    public ResponseEntity<User> updateUser(
            @RequestHeader("Authorization")
            String jwt, @RequestBody User info) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        if(user == null){
            throw new Exception("User not found");
        }
        user = userService.updateUserProfile(user, info);
        return ResponseEntity.ok(user);
    }
}
