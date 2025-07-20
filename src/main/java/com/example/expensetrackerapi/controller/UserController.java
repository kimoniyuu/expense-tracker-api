package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.dto.JwtResponse;
import com.example.expensetrackerapi.dto.LoginRequest;
import com.example.expensetrackerapi.dto.UserDto;
import com.example.expensetrackerapi.entity.User;
import com.example.expensetrackerapi.mapper.UserMapper;
import com.example.expensetrackerapi.service.AuthService;
import com.example.expensetrackerapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@Valid @RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        // แปลงเป็น DTO ก่อนส่งกลับ เพื่อไม่ให้รหัสผ่านรั่วไหล
        return UserMapper.toDto(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateAndGenerateToken(loginRequest);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

