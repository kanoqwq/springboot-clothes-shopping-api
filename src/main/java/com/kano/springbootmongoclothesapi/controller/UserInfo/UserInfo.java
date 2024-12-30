package com.kano.springbootmongoclothesapi.controller.UserInfo;

import com.kano.springbootmongoclothesapi.common.ApiResponse;
import com.kano.springbootmongoclothesapi.model.User;
import com.kano.springbootmongoclothesapi.utils.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.kano.springbootmongoclothesapi.service.UserService;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 用户个人信息
 */
@RestController
@RequestMapping("/api/user")
public class UserInfo {

    @Autowired
    private UserService userService;


    //修改用户
    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization").split("Bearer ")[1];
        String userId = JwtToken.getUserId(token);

        // 密码加密处理
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 可使用 BCrypt 加密
        User updatedUser = userService.updateSelfUser(userId, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

}