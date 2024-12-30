package com.kano.springbootmongoclothesapi.controller.LoginRegister;

import com.kano.springbootmongoclothesapi.common.ApiResponse;
import com.kano.springbootmongoclothesapi.model.User;
import com.kano.springbootmongoclothesapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 注册用户
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    //注册
    @PostMapping
    public ApiResponse doLogin(@RequestBody User user) throws Exception {
            user.setRole("sb");
            HashMap<String,String> res = userService.register(user);
            return new ApiResponse(200,"注册成功",null);
    }

}