package com.crm.controller;

import com.crm.config.JwtUtil;
import com.crm.dto.LoginRequestDTO;
import com.crm.entity.User;
import com.crm.repository.UserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {

        User user = userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(dto.getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(token);


    }
}