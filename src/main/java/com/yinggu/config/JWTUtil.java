package com.yinggu.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtil {
    private final static long EXPIRE_TIME = 4 * 3600 * 1000L;
    private final static String SECRET = "sdfksdakfsdkjafklajiejekjf";

    /**
     * 生成 token
     *
     * @param username 用户名
     * @return token
     */
    public static String jwtCreate(String username) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    //添加存放信息
                    .withClaim("username", username)
                    // 指定令牌过期时间
                    .withExpiresAt(date)
                    // 设置签名(指定密钥方式)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验 token是否正确
     *
     * @param token token
     * @return
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    public static void main(String[] args) {
        String hello = jwtCreate("hello");
        System.out.println(hello);
        System.out.println(verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDU2MjQ4OTksInVzZXJuYW1lIjoiaGVsbG8ifQ.Yd6VkK3xyfU3It6brU9yyNU1WelbFX4HaAoqILDQbFw"));
        System.out.println(getUsername(hello));

    }
}
