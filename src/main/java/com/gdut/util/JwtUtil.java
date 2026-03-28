package com.gdut.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * JWT 令牌工具类
 * 用于生成和解析 JWT 令牌
 */
public class JwtUtil {
    
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString("Bone".getBytes());
    
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000;
    
    /**
     * 生成 JWT 令牌
     * @param claims 自定义声明数据
     * @return JWT 令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    
    /**
     * 解析 JWT 令牌
     * @param token JWT 令牌字符串
     * @return Claims 对象，包含令牌中的所有声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * 从令牌中获取指定声明的值
     * @param token JWT 令牌字符串
     * @param key 声明的键
     * @return 声明的值
     */
    public static Object getClaim(String token, String key) {
        Claims claims = parseToken(token);
        return claims.get(key);
    }
    
    /**
     * 检查令牌是否过期
     * @param token JWT 令牌字符串
     * @return true-未过期，false-已过期
     */
    public static boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 获取过期时间（毫秒）
     * @return 过期时间毫秒数
     */
    public static long getExpirationTime() {
        return EXPIRATION_TIME;
    }
}
