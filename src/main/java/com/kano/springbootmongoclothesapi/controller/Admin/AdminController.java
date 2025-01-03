package com.kano.springbootmongoclothesapi.controller.Admin;

import com.kano.springbootmongoclothesapi.common.ApiResponse;
import com.kano.springbootmongoclothesapi.model.User;
import com.kano.springbootmongoclothesapi.utils.RequestJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.kano.springbootmongoclothesapi.service.UserService;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

/**
 * 用户增删查改
 */
@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Object getAllUsers() {
        if (RequestJWT.getUserInfo() == null) return new ResponseEntity<>(HttpStatusCode.valueOf(401));

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        if (RequestJWT.getUserInfo() == null) return new ResponseEntity<>(HttpStatusCode.valueOf(401));

        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        if (RequestJWT.getUserInfo() == null) return new ResponseEntity<>(HttpStatusCode.valueOf(401));

        // 密码加密处理
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 可使用 BCrypt 加密

        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
        if (RequestJWT.getUserInfo() == null) return new ResponseEntity<>(HttpStatusCode.valueOf(401));

        // 密码加密处理
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 可使用 BCrypt 加密
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public Object deleteUser(@PathVariable String id) {
        if (RequestJWT.getUserInfo() == null) return new ResponseEntity<>(HttpStatusCode.valueOf(401));

        userService.deleteUser(id);
        return new ApiResponse(200,"删除成功",null);
    }
}