package com.spring.blogApp_api.controllers;

import com.spring.blogApp_api.entities.User;
import com.spring.blogApp_api.payloads.UserDto;
import com.spring.blogApp_api.repositories.UserRepo;
import com.spring.blogApp_api.security.CustomUserDetailService;
import com.spring.blogApp_api.security.JwtRequest;
import com.spring.blogApp_api.security.JwtResponse;
import com.spring.blogApp_api.security.JwtTokenHelper;
import com.spring.blogApp_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authorization", description = "Authorization APIs")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    private UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;}

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Operation(summary = "login users", description = "this is api to login already registered user")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login (@Valid @RequestBody JwtRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());

        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenHelper.generateToken(userDetails);

        logger.info("Generated JWT token: {}", token);

        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(()-> {
            return new UsernameNotFoundException( "user with email not found");
        });; // Assuming userService has this method
        UserDto userDto = userToDto(user);

        JwtResponse response = new JwtResponse();
        response.setJwtToken(token);
        response.setUser(userDto); // Set UserDto in response
        return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
    }

    @Operation(summary = "Sign Up User", description = "this is the api to register new user")
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser (@Valid @RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }


    private void doAuthenticate (String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try{
            this.manager.authenticate(authentication);
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("invalid email or password");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> exceptionHandler (BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

}
