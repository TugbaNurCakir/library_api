package com.example.demo.Mapper;

import com.example.demo.Model.Entity.User;
import com.example.demo.Model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDto(User user);
}
