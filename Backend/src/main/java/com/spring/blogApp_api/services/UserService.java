package com.spring.blogApp_api.services;

import com.spring.blogApp_api.payloads.PasswordUpdateDto;
import com.spring.blogApp_api.payloads.UserDto;
import com.spring.blogApp_api.payloads.UserUpdateDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserUpdateDto user, Integer userId);

    UserDto updatePassword(Integer userId, PasswordUpdateDto passwordUpdateDto);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);


}
