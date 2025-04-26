package com.spring.blogApp_api.services.impl;

import com.spring.blogApp_api.config.AppConstants;
import com.spring.blogApp_api.entities.Role;
import com.spring.blogApp_api.entities.User;
import com.spring.blogApp_api.exceptions.ResourceAlreadyExistsException;
import com.spring.blogApp_api.exceptions.ResourceNotFoundException;
import com.spring.blogApp_api.payloads.PasswordUpdateDto;
import com.spring.blogApp_api.payloads.UserDto;
import com.spring.blogApp_api.payloads.UserUpdateDto;
import com.spring.blogApp_api.repositories.RoleRepo;
import com.spring.blogApp_api.repositories.UserRepo;
import com.spring.blogApp_api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }
    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }


    @Override
    public UserDto registerNewUser(UserDto userDto) {

        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email is already registered with another user.");
        }

        User user = this.modelMapper.map(userDto, User.class);

        //encoded password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));

        user.setName(userUpdateDto.getName());
        user.setEmail(userUpdateDto.getEmail());
        user.setAbout(userUpdateDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto updatePassword(Integer userId, PasswordUpdateDto passwordUpdateDto) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

        // Check if the old password matches
        if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // Set and encode the new password
        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        User updatedUser = this.userRepo.save(user);

        return this.userToDto(updatedUser);
    }


    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users= this.userRepo.findAll();

        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        this.userRepo.delete(user);
    }
}
