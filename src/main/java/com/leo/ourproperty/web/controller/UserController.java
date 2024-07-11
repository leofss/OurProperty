package com.leo.ourproperty.web.controller;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.entity.User;
import com.leo.ourproperty.repository.projection.UserProjection;
import com.leo.ourproperty.service.UserService;
import com.leo.ourproperty.web.dto.*;
import com.leo.ourproperty.web.dto.mapper.PageableMapper;
import com.leo.ourproperty.web.dto.mapper.PropertyMapper;
import com.leo.ourproperty.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create user", description = "Available only for users with ADMIN role")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserDto userDto){
        User user = UserMapper.toUserEntity(userDto);
        userService.create(user);
        return ResponseEntity.status(201).body(UserMapper.toUserDto(user));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all users", description = "Available only for users with ADMIN role")
    public ResponseEntity<PageableDto> getAll(Pageable pageable){
        Page<UserProjection> user = userService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.pageableDto(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete single user", description = "Available only for users with ADMIN role")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Edit single user", description = "Available only for users with ADMIN role")
    public ResponseEntity<UserResponseDto> edit(@PathVariable Long id, @Valid @RequestBody UserDto userDto){
        User user = userService.edit(id, userDto);
        return ResponseEntity.ok().body(UserMapper.toUserDto(user));
    }


}
