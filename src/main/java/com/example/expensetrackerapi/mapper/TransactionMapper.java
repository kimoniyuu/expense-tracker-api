package com.example.expensetrackerapi.mapper;
import com.example.expensetrackerapi.dto.TransactionDto;
import com.example.expensetrackerapi.entity.Transaction;
public class TransactionMapper {
    public static TransactionDto toDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getType());
        if (transaction.getCategory() != null) {
            dto.setCategory(CategoryMapper.toDto(transaction.getCategory()));
        }
        return dto;
    }
}