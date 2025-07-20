package com.example.expensetrackerapi.dto;
import com.example.expensetrackerapi.entity.Transaction;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class TransactionDto {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private Transaction.TransactionType type;
    private CategoryDto category;
}