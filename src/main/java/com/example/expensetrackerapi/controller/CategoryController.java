package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.dto.CategoryDto;
import com.example.expensetrackerapi.dto.CreateCategoryRequest;
import com.example.expensetrackerapi.entity.Category;
import com.example.expensetrackerapi.mapper.CategoryMapper;
import com.example.expensetrackerapi.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/users/{userId}/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@PathVariable Long userId, @Valid @RequestBody CreateCategoryRequest request) {
        Category newCategory = new Category();
        newCategory.setName(request.getName());

        Category createdCategory = categoryService.createCategoryForUser(userId, newCategory);

        return CategoryMapper.toDto(createdCategory);
    }

    @GetMapping("/users/{userId}/categories")
    public List<CategoryDto> getAllCategoriesByUserId(@PathVariable Long userId) {
        List<Category> categories = categoryService.getCategoriesByUserId(userId);
        return categories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
