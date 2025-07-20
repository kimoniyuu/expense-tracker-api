package com.example.expensetrackerapi.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class CreateCategoryRequest {
    @NotBlank
    private String name;
}