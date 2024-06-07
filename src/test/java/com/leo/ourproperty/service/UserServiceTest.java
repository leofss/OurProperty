package com.leo.ourproperty.service;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.entity.User;
import com.leo.ourproperty.exception.EntityNotFoundExecption;
import com.leo.ourproperty.repository.UserRepository;
import com.leo.ourproperty.repository.projection.UserProjection;
import com.leo.ourproperty.web.dto.PropertyDto;
import com.leo.ourproperty.web.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
    }

    private User initializeUser(){
        return new User(
                1L,
                "Sample Name",
                "sample@gmail.com",
                "18268321067",
                User.Role.ADMIN,
                "1234",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "UserCreatedBy",
                "UserModifiedBy"
        );
    }
    @Test
    void shouldSuccessfullySaveUser() {
        User user = initializeUser();

        when(userRepository.save(any(User.class))).thenReturn(user);

        User userCreated = userService.create(user);

        assertNotNull(userCreated);
        assertEquals(user.getEmail(), userCreated.getEmail());
        assertEquals(user.getName(), userCreated.getName());
        assertEquals(user.getCpf(), userCreated.getCpf());
        assertEquals(user.getRole().name(), userCreated.getRole().name());
        assertEquals(user.getPassword(), userCreated.getPassword());
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void shouldEditUserSuccessfully() {
        User user = initializeUser();

        UserDto userDto = new UserDto();
        userDto.setCpf("43729879006");
        userDto.setName("Novo Nome");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.edit(user.getId(), userDto);

        assertNotNull(updatedUser);
        assertEquals(userDto.getCpf(), updatedUser.getCpf());
        assertEquals(userDto.getName(), updatedUser.getName());

    }

    @Test
    void shouldFindUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.findByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void shouldNotFoundUserByEmail() {
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        EntityNotFoundExecption exception = assertThrows(EntityNotFoundExecption.class, () -> {
            userService.findByEmail(email);
        });

        assertEquals("User with email " + email + " not found", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void shouldFindRoleByEmail() {
        String email = "test@example.com";
        User.Role expectedRole = User.Role.ADMIN;
        when(userRepository.findRoleByEmail(email)).thenReturn(expectedRole);

        User.Role result = userService.findRoleByEmail(email);

        assertNotNull(result);
        assertEquals(expectedRole, result);
        verify(userRepository, times(1)).findRoleByEmail(email);
    }

    @Test
    void shouldNotFindRoleByEmail() {
        String email = "notfound@example.com";
        when(userRepository.findRoleByEmail(email)).thenReturn(null);

        User.Role result = userService.findRoleByEmail(email);

        assertNull(result);
        verify(userRepository, times(1)).findRoleByEmail(email);
    }

    @Test
    void shouldFindAll(){
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        UserProjection userProjection = new UserProjection() {
            @Override
            public String getEmail() {
                return "test@example.com";
            }

            @Override
            public String getCpf() {
                return "44479235051";
            }

            @Override
            public String getPassword() {
                return "1234";
            }

            @Override
            public String getName() {
                return "Test User";
            }

        };
        Page<UserProjection> userProjections = new PageImpl<>(Collections.singletonList(userProjection));

        when(userRepository.findAllPageable(pageable)).thenReturn(userProjections);

        // Act
        Page<UserProjection> result = userService.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userProjection.getEmail(), result.getContent().get(0).getEmail());
        assertEquals(userProjection.getName(), result.getContent().get(0).getName());
        assertEquals(userProjection.getCpf(), result.getContent().get(0).getCpf());
        assertEquals(userProjection.getPassword(), result.getContent().get(0).getPassword());

        verify(userRepository, times(1)).findAllPageable(pageable);
    }
}