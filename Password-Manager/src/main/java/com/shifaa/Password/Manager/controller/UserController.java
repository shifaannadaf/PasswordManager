package com.shifaa.Password.Manager.controller;

import com.shifaa.Password.Manager.config.JWTUtil;
import com.shifaa.Password.Manager.model.User;
import com.shifaa.Password.Manager.model.AuthRequest;
import com.shifaa.Password.Manager.model.AuthResponse;
import com.shifaa.Password.Manager.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ REGISTER endpoint (updated to accept JSON body)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        User existing = userService.findByUsername(request.getUsername());
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
        }

        userService.registerUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("User registered successfully!");
    }

    // ✅ LOGIN endpoint (already correct)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
