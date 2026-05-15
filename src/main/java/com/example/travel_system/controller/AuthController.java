package com.example.travel_system.controller;

import com.example.travel_system.dto.request.LoginRequest;
import com.example.travel_system.dto.request.RegisterRequest;
import com.example.travel_system.dto.response.AuthResponse;
import com.example.travel_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
//    @GetMapping("/test-password")
//    public String testPassword() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String rawPassword = "password";
//        String hashInDB = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh8y";
//        boolean matches = encoder.matches(rawPassword, hashInDB);
//        return "BCrypt matches: " + matches;
//    }
//@GetMapping("/generate-hash")
//public String generateHash() {
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    return encoder.encode("password");
//}

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Đăng ký thành công!");
    }
}