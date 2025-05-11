package com.company_manager.api.controllers;

import com.company_manager.api.dtos.UserDTO;
import com.company_manager.api.models.UserModel;
import com.company_manager.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping
    private ResponseEntity<UserModel> createuser(@RequestBody @Valid UserDTO body) {
        UserModel user = userService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
