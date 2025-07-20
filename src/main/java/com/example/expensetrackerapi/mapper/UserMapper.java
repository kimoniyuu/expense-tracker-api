package com.example.expensetrackerapi.mapper;

import com.example.expensetrackerapi.dto.UserDto;
import com.example.expensetrackerapi.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
