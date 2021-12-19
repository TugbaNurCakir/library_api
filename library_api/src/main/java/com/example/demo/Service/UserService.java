package com.example.demo.Service;

import com.example.demo.Model.Entity.User;
import com.example.demo.Model.dto.UserDTO;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import static com.example.demo.Mapper.UserMapper.USER_MAPPER;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO getUserDto(int id){
        User user = getUser(id);
        return USER_MAPPER.toUserDto(user);
    }

    private User getUser(int id) {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found."));
    }
    protected void incrementRegistrationsCount(int id){
        userRepository.incrementRegisteredBookCount(id);
    }
}
