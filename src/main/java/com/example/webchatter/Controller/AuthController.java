package com.example.webchatter.Controller;

import com.example.webchatter.api.dto.UserDto;
import com.example.webchatter.api.request.GetTokenRequest;
import com.example.webchatter.api.request.SignupRequest;
import com.example.webchatter.api.response.MessageResponse;
import com.example.webchatter.api.response.TokenResponse;
import com.example.webchatter.model.Users;
import com.example.webchatter.repository.UserRepository;
import com.example.webchatter.security.JwtService;
import com.example.webchatter.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    //private final DtoMapper dtoMapper;
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(SignupRequest request) {
        if (userRepository.existsByLogin(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        Users user = new Users(request.getPassword(),request.getPassword(), request.getDisplayName());
        userRepository.save(user);

        Authentication authUser = authorize(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(
                TokenResponse.builder()
                        .token(jwtService.generateToken(authUser))
                        .username(user.getUsername())
                        .displayName(user.getDisplayName())
                        .build()
        );
    }
    @PostMapping("/sign-in")
    public TokenResponse signIn(GetTokenRequest getTokenRequest) {
        Authentication authentication = authorize(getTokenRequest.getUsername(), getTokenRequest.getPassword());
        Users user = (Users) authentication.getPrincipal();
        return TokenResponse.builder()
                .token(jwtService.generateToken(authentication))
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .build();
    }

    private Authentication authorize(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
//    @Bean
//    public DtoMapper dtoMapper(){
//        return new DtoMapper() {}
//    }

}