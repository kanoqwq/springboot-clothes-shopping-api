package com.kano.springbootmongoclothesapi.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;

public class RequestJWT {
    public static HashMap<String,String> getUserInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization").split("Bearer ")[1];
        String userId = JwtToken.getUserId(token);
        HashMap<String,String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        return map;
    }
}
