package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.dto.requests.LoginRequest;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:5173")
public class UserController {
    @Autowired
    UserService userService;

    //    Register
    @PostMapping("/registerUser")
    @CrossOrigin("http://localhost:5173")
    public User addUser(@Valid @RequestBody User user) {
        return userService.registerUser(user);
    }

    //    Login
    @PostMapping("/loginUser")
    @CrossOrigin("http://localhost:5173")
    public String loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }
}
