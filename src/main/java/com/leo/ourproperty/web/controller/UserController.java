package com.leo.ourproperty.web.controller;

import com.leo.ourproperty.entity.User;
import com.leo.ourproperty.repository.projection.UserProjection;
import com.leo.ourproperty.service.UserService;
import com.leo.ourproperty.web.api.UserAPI;
import com.leo.ourproperty.web.dto.*;
import com.leo.ourproperty.web.dto.mapper.PageableMapper;
import com.leo.ourproperty.web.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController implements UserAPI {
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> create(UserDto userDto) {
        User user = UserMapper.toUserEntity(userDto);
        userService.create(user);
        return ResponseEntity.status(201).body(UserMapper.toUserDto(user));
    }

    @Override
    public ResponseEntity<PageableDto> getAll(Pageable pageable) {
        Page<UserProjection> user = userService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.pageableDto(user));
    }

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }

    @Override
    public ResponseEntity<UserResponseDto> edit(Long id, UserDto userDto) {
        User user = userService.edit(id, userDto);
        return ResponseEntity.ok().body(UserMapper.toUserDto(user));
    }


}
