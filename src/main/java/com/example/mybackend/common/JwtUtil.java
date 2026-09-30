package com.example.mybackend.common;

import com.example.mybackend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // 密钥，生产环境应该放配置文件
    private static final String SECRET = "my-backend-secret-key-must-be-at-least-256-bits-long!!";
    private static final long EXPIRE_MS = 24 * 60 * 60 * 1000;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // 生成 token，里面存 userId、手机号、昵称
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("phone", user.getPhone());
        claims.put("nickname", user.getNickname());

        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_MS))
                .signWith(getKey())
                .compact();
    }

    // 解析 token，返回全部 claims；失败返回 null
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }
}
