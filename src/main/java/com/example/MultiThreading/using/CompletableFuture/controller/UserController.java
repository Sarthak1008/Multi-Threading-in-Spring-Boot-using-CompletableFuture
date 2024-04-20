package com.example.MultiThreading.using.CompletableFuture.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.MultiThreading.using.CompletableFuture.entities.User;
import com.example.MultiThreading.using.CompletableFuture.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    // Endpoint to save users from uploaded files
    @PostMapping(value = "/users", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = "application/json")
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        // Iterate through uploaded files and save users asynchronously
        for (MultipartFile file : files) {
            service.saveUsers(file);
        }
        // Return success response
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Endpoint to retrieve all users
    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
        // Retrieve all users asynchronously and return ResponseEntity when complete
        return service.findAllUsers().thenApply(ResponseEntity::ok);
    }

    // Endpoint to demonstrate parallel retrieval of users
    @GetMapping(value = "/getUsersByThread", produces = "application/json")
    public ResponseEntity getUsers() {
        // Retrieve users from three different threads in parallel
        CompletableFuture<List<User>> users1 = service.findAllUsers();
        CompletableFuture<List<User>> users2 = service.findAllUsers();
        CompletableFuture<List<User>> users3 = service.findAllUsers();
        // Wait for all threads to complete
        CompletableFuture.allOf(users1, users2, users3).join();
        // Return success response
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
