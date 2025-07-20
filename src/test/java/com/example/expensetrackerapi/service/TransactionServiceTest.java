package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.entity.Transaction;
import com.example.expensetrackerapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 1. เปิดใช้งาน Mockito
class TransactionServiceTest {

    @Mock // 2. สร้าง "ตัวปลอม" ของ Repository
    private TransactionRepository transactionRepository;

    @InjectMocks // 3. สร้าง "ตัวจริง" ของ Service ที่เราจะทดสอบ และฉีด "ตัวปลอม" เข้าไป
    private TransactionService transactionService;

    private List<Transaction> mockTransactions;

    @BeforeEach // 4. เมธอดนี้จะทำงาน "ก่อน" ทุกๆ @Test
    void setUp() {
        // 5. สร้างข้อมูลจำลองสำหรับใช้ในการทดสอบ
        Transaction expense1 = new Transaction();
        expense1.setType(Transaction.TransactionType.EXPENSE);
        expense1.setAmount(new BigDecimal("100.50"));

        Transaction expense2 = new Transaction();
        expense2.setType(Transaction.TransactionType.EXPENSE);
        expense2.setAmount(new BigDecimal("49.50"));

        Transaction income1 = new Transaction();
        income1.setType(Transaction.TransactionType.INCOME);
        income1.setAmount(new BigDecimal("1000.00"));

        mockTransactions = List.of(expense1, expense2, income1);
    }

    @Test // 6. ประกาศว่านี่คือ Test Case
    void calculateTotalExpensesForUser_ShouldReturnCorrectSumOfExpenses() {
        // Arrange (จัดฉาก)
        Long userId = 1L;
        // 7. สั่งให้ "ตัวปลอม" ทำงานตามที่เราต้องการ
        when(transactionRepository.findByUserId(userId)).thenReturn(mockTransactions);

        // Act (ลงมือ)
        // 8. เรียกใช้เมธอดที่เราต้องการทดสอบจริงๆ
        BigDecimal totalExpenses = transactionService.calculateTotalExpensesForUser(userId);

        // Assert (ตรวจสอบผลลัพธ์)
        // 9. ตรวจสอบว่าผลลัพธ์ที่ได้ถูกต้องหรือไม่ (100.50 + 49.50 = 150.00)
        assertThat(totalExpenses).isEqualByComparingTo(new BigDecimal("150.00"));
    }
}