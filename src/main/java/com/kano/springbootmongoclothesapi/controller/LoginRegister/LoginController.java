package com.kano.springbootmongoclothesapi.controller.LoginRegister;

import com.kano.springbootmongoclothesapi.model.User;
import com.kano.springbootmongoclothesapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 登录用户
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private UserService userService;

    //登录
    @PostMapping
    public ResponseEntity<User> doLogin(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

}