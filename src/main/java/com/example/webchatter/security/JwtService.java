package com.example.webchatter.security;

import com.example.webchatter.model.Users;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static com.example.webchatter.util.Constants.AUTH_HEADER;
import static com.example.webchatter.util.Constants.TOKEN_HEADER_SUFFIX;


@Service
@Slf4j
public class JwtService {

    @Value("${app.security.jwt.secret}")
    private String jwtSecret;
    @Value("${app.security.jwt.duration}")
    private int jwtExpirationTime;

    public String generateToken(Authentication authentication) {
        Users userPrincipal = (Users) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationTime))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {}
        return false;
    }

    public Optional<String> parseToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_HEADER_SUFFIX)) {
            return Optional.of(headerAuth.substring(TOKEN_HEADER_SUFFIX.length()));
        }
        return Optional.empty();
    }

}