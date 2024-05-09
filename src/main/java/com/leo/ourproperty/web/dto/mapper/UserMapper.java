package com.leo.ourproperty.web.dto.mapper;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.entity.User;
import com.leo.ourproperty.web.dto.PropertyDto;
import com.leo.ourproperty.web.dto.PropertyResponseDto;
import com.leo.ourproperty.web.dto.UserDto;
import com.leo.ourproperty.web.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User toUserEntity(UserDto dto){
        return new ModelMapper().map(dto, User.class);
    }

    public static UserResponseDto toUserDto(User user){
        return new ModelMapper().map(user, UserResponseDto.class);
    }
}
