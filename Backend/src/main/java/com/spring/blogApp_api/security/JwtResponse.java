package com.spring.blogApp_api.security;

import com.spring.blogApp_api.payloads.UserDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {

    private String jwtToken;

    private UserDto user;


}
