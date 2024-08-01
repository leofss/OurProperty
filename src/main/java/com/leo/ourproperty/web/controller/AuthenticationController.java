package com.leo.ourproperty.web.controller;

import com.leo.ourproperty.exception.EntityNotFoundExecption;
import com.leo.ourproperty.jwt.JwtToken;
import com.leo.ourproperty.jwt.JwtUserDetailsService;
import com.leo.ourproperty.web.api.AuthenticationAPI;
import com.leo.ourproperty.web.dto.UserLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController

public class AuthenticationController implements AuthenticationAPI {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<?> authenticate(UserLoginDto userLoginDto, HttpServletRequest req) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthenticate(userLoginDto.getEmail());
            return ResponseEntity.ok(token);

        }catch (AuthenticationException ex){
            throw new AccessDeniedException("Invalid credentials");
        }
    }
}
