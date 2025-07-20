package com.example.expensetrackerapi.mapper;

import com.example.expensetrackerapi.dto.CategoryDto;
import com.example.expensetrackerapi.entity.Category;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}
