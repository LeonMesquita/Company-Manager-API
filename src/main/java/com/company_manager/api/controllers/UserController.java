package com.company_manager.api.controllers;

import com.company_manager.api.dtos.UserDTO;
import com.company_manager.api.models.UserModel;
import com.company_manager.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    private ResponseEntity<List<UserModel>> getUsers() {
        List<UserModel> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @PostMapping
    private ResponseEntity<UserModel> createuser(@RequestBody @Valid UserDTO body) {
        UserModel user = userService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserModel> findUserById(@PathVariable Long id) {
        UserModel user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @PutMapping("/{id}")
    private ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO body) {
        UserModel user = userService.update(id, body);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
