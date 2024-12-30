package com.kano.springbootmongoclothesapi.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class JwtToken {

    private static final String secretKey = "wocaonima"; // 用于签名的密钥

    public String generateToken(String userId,String role) {
        return Jwts.builder()
                .setSubject(userId + "|" + role)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 设置 token 的过期时间（1小时）
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 从 JWT 中提取用户role
    public static String getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody(); // 获取 JWT 的主体部分
        String str = claims.getSubject().split("\\|")[1];
        return str; // 获取 token 中的主题（）
    }
    // 从 JWT 中提取用户id
    public static String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody(); // 获取 JWT 的主体部分
        String str = claims.getSubject().split("\\|")[0];
        return str; // 获取 token 中的主题（）
    }

    // 校验 JWT 是否有效
    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date()); // 判断过期时间
    }
}