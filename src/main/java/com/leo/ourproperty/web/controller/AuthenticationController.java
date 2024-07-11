package com.leo.ourproperty.web.controller;

import com.leo.ourproperty.exception.EntityNotFoundExecption;
import com.leo.ourproperty.jwt.JwtToken;
import com.leo.ourproperty.jwt.JwtUserDetailsService;
import com.leo.ourproperty.web.dto.UserLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@RestController

public class AuthenticationController {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;


    @PostMapping
    @Operation(summary = "User authentication")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletRequest req){
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthenticate(userLoginDto.getEmail());
            return ResponseEntity.ok(token);

        }catch (AuthenticationException ex){
            throw new EntityNotFoundExecption("User with email " + userLoginDto.getEmail() + " not found");
        }
    }
}
