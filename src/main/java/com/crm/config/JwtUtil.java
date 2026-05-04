package com.crm.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkey";

    public String generateToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.ES256, SECRET)
                .compact();
    }

    public String extractUsername(String token)
    {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

}
