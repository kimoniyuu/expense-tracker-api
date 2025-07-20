package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.entity.User;
import com.example.expensetrackerapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 1. บอกว่านี่คือ Integration Test ให้โหลด Spring Boot ทั้งระบบ
@AutoConfigureMockMvc // 2. ตั้งค่า MockMvc ให้เราอัตโนมัติ
class UserControllerTest {

    @Autowired // 3. ฉีด MockMvc เข้ามาเพื่อใช้ยิง API จำลอง
    private MockMvc mockMvc;

    @Autowired // 4. ฉีด ObjectMapper เข้ามาเพื่อแปลง Java Object เป็น JSON
    private ObjectMapper objectMapper;

    @Autowired // 5. ฉีด Repository จริงๆ เข้ามาเพื่อใช้ตรวจสอบข้อมูลในฐานข้อมูล
    private UserRepository userRepository;

    @BeforeEach // 6. ล้างฐานข้อมูลก่อนทุกๆ Test Case เพื่อให้การทดสอบไม่ขึ้นต่อกัน
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void registerUser_ShouldCreateUserSuccessfully() throws Exception {
        // Arrange (จัดฉาก)
        User newUser = new User();
        newUser.setUsername("testuser");
        newUser.setPassword("password123");

        // Act (ลงมือ) & Assert (ตรวจสอบผลลัพธ์)
        mockMvc.perform(post("/api/users/register") // 7. ยิง POST request จำลอง
                        .contentType(MediaType.APPLICATION_JSON) // 8. บอกว่า Body ที่ส่งไปเป็น JSON
                        .content(objectMapper.writeValueAsString(newUser))) // 9. แปลง newUser เป็น JSON string
                .andExpect(status().isCreated()) // 10. ตรวจสอบว่า HTTP Status คือ 201 Created
                .andExpect(jsonPath("$.id").exists()) // 11. ตรวจสอบว่าใน JSON ที่ตอบกลับมา มี field "id" อยู่
                .andExpect(jsonPath("$.username").value("testuser")); // 12. ตรวจสอบว่า username ถูกต้อง

        // 13. ตรวจสอบในฐานข้อมูลจริงๆ ว่ามี user นี้อยู่หรือไม่
        assertThat(userRepository.findByUsername("testuser")).isPresent();
    }
}
