package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.repository.CategoryRepository;
import com.example.expensetrackerapi.repository.TransactionRepository;
import com.example.expensetrackerapi.repository.UserRepository;
import com.example.expensetrackerapi.entity.Category;
import com.example.expensetrackerapi.entity.Transaction;
import com.example.expensetrackerapi.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(UserRepository userRepository, CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransactionForUser(Long userId, Long categoryId, Transaction transaction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        if (!Objects.equals(category.getUser().getId(), user.getId())) {
            throw new IllegalStateException("Category does not belong to the user");
        }
        transaction.setUser(user);
        transaction.setCategory(category);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public BigDecimal calculateTotalExpensesForUser(Long userId) {
        // 1. ดึง Transaction ทั้งหมดของผู้ใช้ออกมา
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        // 2. ใช้ Java Stream ในการคำนวณ
        return transactions.stream()
                // กรองเอาเฉพาะรายการที่เป็น "รายจ่าย"
                .filter(transaction -> transaction.getType() == Transaction.TransactionType.EXPENSE)
                // แปลงสายน้ำของ Transaction ให้กลายเป็นสายน้ำของ BigDecimal
                .map(Transaction::getAmount)
                // รวมยอดทั้งหมดในสายน้ำ โดยเริ่มจาก 0
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
