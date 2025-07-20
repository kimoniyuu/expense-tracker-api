package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.dto.CreateTransactionRequest;
import com.example.expensetrackerapi.dto.TransactionDto;
import com.example.expensetrackerapi.entity.Transaction;
import com.example.expensetrackerapi.mapper.TransactionMapper;
import com.example.expensetrackerapi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/users/{userId}/categories/{categoryId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto createTransaction(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @Valid @RequestBody CreateTransactionRequest request) {

        Transaction newTransaction = new Transaction();
        newTransaction.setDescription(request.getDescription());
        newTransaction.setAmount(request.getAmount());
        newTransaction.setDate(request.getDate());
        newTransaction.setType(request.getType());

        Transaction createdTransaction = transactionService.createTransactionForUser(userId, categoryId, newTransaction);

        return TransactionMapper.toDto(createdTransaction);
    }

    @GetMapping("/users/{userId}/transactions")
    public List<TransactionDto> getAllTransactionsByUserId(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
        return transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }
}