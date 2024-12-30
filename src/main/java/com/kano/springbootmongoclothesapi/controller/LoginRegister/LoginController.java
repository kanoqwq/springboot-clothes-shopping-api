package com.kano.springbootmongoclothesapi.controller.LoginRegister;

import com.kano.springbootmongoclothesapi.common.ApiResponse;
import com.kano.springbootmongoclothesapi.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 登录用户
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private UserService userService;


    //登录
    // POST 请求，将 JSON 转换为 Map
    @PostMapping
    public ApiResponse doLogin(@RequestBody Map<String, String> userLogin) throws Exception {

        String username = userLogin.get("username");
        String password = userLogin.get("password");
        if(username == null || password == null) {  return new ApiResponse(500,"参数错误",null); }

        Object res =  userService.login(username,password);

        if(res.equals(false)){
            return new ApiResponse(500,"用户名或密码错误",null);
        }

        return new ApiResponse(200,"登录成功",res);
    }

}