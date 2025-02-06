package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exceptions.DataMismatchException;
import com.example.demo.repository.UserRepo;
import com.example.demo.dto.requests.LoginRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@Slf4j
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User registerUser(@Valid User user) {
        if (userRepo.existsById(user.getEmail())) {
            throw new ValidationException("Email is already registered.");
        }
        return userRepo.save(user);
    }

    //    public Boolean loginUser(LoginRequest loginRequest){
    public String loginUser(@Valid LoginRequest loginRequest) {
        System.out.println(loginRequest);
        Optional<User> user1 = userRepo.findById(loginRequest.getUserId());
        if (!user1.isPresent()) {
            throw new DataMismatchException("User not found");
        }
        User user = user1.get();
//        log.info("User Details");
//        log.info(user.getEmail());
//
//        log.info("Login Request details");
//        log.info(loginRequest.getUserId());
//        log.info(loginRequest.getPassword());

        if ((!user.getPassword().equals(loginRequest.getPassword())) ||
                (!user.getEmail().equals(loginRequest.getUserId()))) {
            throw new DataMismatchException("Incorrect email or password");
        }

        return "Login successful";
    }
}
