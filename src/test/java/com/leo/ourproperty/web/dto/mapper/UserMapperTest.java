package com.leo.ourproperty.web.dto.mapper;

import com.leo.ourproperty.entity.User;
import com.leo.ourproperty.web.dto.UserDto;
import com.leo.ourproperty.web.dto.UserResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void shouldMapUserDtoToUserEntity(){
        String adminRole = User.Role.ADMIN.name();
        UserDto dto = new UserDto(
                "Leo",
                "leo@gmail.com",
                "63480853099",
                "1234",
                adminRole);
        User user = UserMapper.toUserEntity(dto);

        assertNotNull(dto.getName());
        assertNotNull(dto.getEmail());
        assertNotNull(dto.getCpf());
        assertNotNull(dto.getPassword());
        assertNotNull(dto.getRole());

        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getCpf());
        assertNotNull(user.getPassword());
        assertNotNull(user.getRole());

        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getCpf(), user.getCpf());
        assertEquals(dto.getPassword(), user.getPassword());

        String dtoRole = dto.getRole();
        String userRole = user.getRole().toString();
        assertEquals(dtoRole, userRole);
    }

    @Test
    void shouldMapUserEntitytoUserResponseDto(){
        User user = new User(
                10L,
                "leo",
                "leo@gmail.com",
                "35395914005",
                User.Role.ADMIN,
                "123456",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "UserCreatedBy",
                "UserModifiedBy"
        );

        UserResponseDto userResponseDtoDto = UserMapper.toUserDto(user);

        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getCpf());
        assertNotNull(user.getPassword());
        assertNotNull(user.getRole());
        assertNotNull(user.getPassword());
        assertNotNull(user.getCreatedBy());
        assertNotNull(user.getCreatedDate());
        assertNotNull(user.getCreatedBy());
        assertNotNull(user.getLastModifiedBy());

        assertNotNull(userResponseDtoDto.getCpf());
        assertNotNull(userResponseDtoDto.getRole());
        assertNotNull(userResponseDtoDto.getName());
        assertNotNull(userResponseDtoDto.getEmail());


        assertEquals(user.getCpf(), userResponseDtoDto.getCpf());
        assertEquals(user.getName(), userResponseDtoDto.getName());
        assertEquals(user.getEmail(), userResponseDtoDto.getEmail());

        String userEntity = user.getRole().name();
        String userResponseDto = userResponseDtoDto.getRole();
        assertEquals(userEntity, userResponseDto);

    }
}