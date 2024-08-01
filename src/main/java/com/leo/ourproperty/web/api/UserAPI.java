package com.leo.ourproperty.web.api;

import com.leo.ourproperty.web.dto.PageableDto;
import com.leo.ourproperty.web.dto.UserDto;
import com.leo.ourproperty.web.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/user")
@Tag(name = "V1 - User")
public interface UserAPI {
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create user", description = "Available only for users with ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User sucessfully created"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity"),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
            @ApiResponse(responseCode = "409", description = "User with cpf/email alreay exists"),

    })
    ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserDto userDto);

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all users", description = "Available only for users with ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users sucessfully listed"),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
    })
    ResponseEntity<PageableDto> getAll(Pageable pageable);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete single user", description = "Available only for users with ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Users sucessfully deleted"),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
            @ApiResponse(responseCode = "404", description = "User not found"),

    })
    void delete(@PathVariable Long id);

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Edit single user", description = "Available only for users with ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users sucessfully edited"),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
            @ApiResponse(responseCode = "409", description = "User with cpf/email alreay exists"),

    })
    ResponseEntity<UserResponseDto> edit(@PathVariable Long id, @Valid @RequestBody UserDto userDto);
}
