package com.example.expensetrackerapi.dto;
import com.example.expensetrackerapi.entity.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class CreateTransactionRequest {
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private LocalDate date;
    @NotNull
    private Transaction.TransactionType type;
}