package com.kano.springbootmongoclothesapi.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtToken {

    private final String secretKey = "wocaonima"; // 用于签名的密钥

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 设置 token 的过期时间（1小时）
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 从 JWT 中提取用户名
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody(); // 获取 JWT 的主体部分
        return claims.getSubject(); // 获取 token 中的主题（即用户名）
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