package com.spring.blogApp_api.controllers;

import com.spring.blogApp_api.payloads.ApiResponse;
import com.spring.blogApp_api.payloads.PasswordUpdateDto;
import com.spring.blogApp_api.payloads.UserDto;
import com.spring.blogApp_api.payloads.UserUpdateDto;
import com.spring.blogApp_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User APIs")
public class UserController {

    @Autowired
    private UserService userService;

    //POST
    @PostMapping("/")
    @Operation(summary = "create new user", description = "This api creates users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT
    @PutMapping("/{userId}")
    @Operation(summary = "Update user info", description = "This api updates user info provided the User Id")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable("userId") Integer userId) {
        UserDto updatedUser = this.userService.updateUser(userUpdateDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    //UPDATE PASSWORD
    @PutMapping("/{userId}/password")
    public ResponseEntity<UserDto> updatePassword(
            @PathVariable Integer userId,
            @Valid @RequestBody PasswordUpdateDto passwordUpdateDto) {

        UserDto updatedUser = this.userService.updatePassword(userId, passwordUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }


    //DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User", description = "This api deletes user provided the User Id")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }


    //GET ALL USERS
    @GetMapping("/")
    @Operation(summary = "Get all users", description = "This api returns a list of all the users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    //GET SINGLE USER
    @GetMapping("/{userId}")
    @Operation(summary = "Get single user", description = "This api finds user provided Id")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId) {
         return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}
