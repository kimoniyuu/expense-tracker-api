package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.dto.LoginRequest;
import com.example.expensetrackerapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String authenticateAndGenerateToken(LoginRequest loginRequest) {
        // 1. สร้าง "ตั๋ว" สำหรับยื่นให้ผู้จัดการตรวจสอบ
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // 2. ถ้าตรวจสอบผ่าน ให้เก็บข้อมูลผู้ใช้ที่ล็อกอินสำเร็จไว้
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. ดึงข้อมูล UserDetails ออกมาเพื่อนำไปสร้าง Token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 4. เรียกใช้ JwtUtil เพื่อสร้าง "บัตรผ่าน"
        return jwtUtil.generateToken(userDetails);
    }
}
