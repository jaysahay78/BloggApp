package com.spring.blogApp_api.security;

import com.spring.blogApp_api.entities.User;
import com.spring.blogApp_api.repositories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to find user with email: {}", username);
        User user = userRepo.findByEmail(username).orElseThrow(()-> {
            logger.error("User with email {} not found", username);
            return new UsernameNotFoundException( "user with email "+username+" not found");
        });

        logger.info("User {} found with roles: {}", user.getEmail(), user.getRoles());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }
}
