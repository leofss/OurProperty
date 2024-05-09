package com.leo.ourproperty.web.dto.mapper;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.web.dto.PropertyDto;
import com.leo.ourproperty.web.dto.PropertyResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyMapper {
    public static Property toPropertyEntity(PropertyDto dto){
        return new ModelMapper().map(dto, Property.class);
    }

    public static PropertyResponseDto toPropertyDto(Property property){
        return new ModelMapper().map(property, PropertyResponseDto.class);
    }

}
