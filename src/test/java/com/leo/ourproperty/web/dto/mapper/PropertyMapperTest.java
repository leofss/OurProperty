package com.leo.ourproperty.web.dto.mapper;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.web.dto.PropertyDto;
import com.leo.ourproperty.web.dto.PropertyResponseDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertyMapperTest {

    @Test
    void shouldMapPropertyDtoToPropertyEntity(){
        List<String> charateristicsList = new ArrayList<>();
        charateristicsList.add("Pool");
        charateristicsList.add("Karaoke");

        BigDecimal condoPrice = new BigDecimal(2000);
        BigDecimal taxPrice = new BigDecimal(3000);
        BigDecimal sqrPrice = new BigDecimal(4000);

        PropertyDto dto = new PropertyDto(
                "Sample Title",
                20.5,
                21.5,
                5,
                4,
                3,
                2,
                "Sample Description",
                "Sample Address",
                condoPrice,
                taxPrice,
                sqrPrice,
                charateristicsList
        );

        Property property = PropertyMapper.toPropertyEntity(dto);

        assertNotNull(dto.getTitle());
        assertNotNull(dto.getArea());
        assertNotNull(dto.getTotalArea());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getAddress());
        assertNotNull(dto.getCondoPrice());
        assertNotNull(dto.getTaxPrice());
        assertNotNull(dto.getSquarerootPrice());
        assertNotNull(dto.getCharacteristics());

        assertNotNull(property.getTitle());
        assertNotNull(property.getArea());
        assertNotNull(property.getTotalArea());
        assertNotNull(property.getDescription());
        assertNotNull(property.getAddress());
        assertNotNull(property.getCondoPrice());
        assertNotNull(property.getTaxPrice());
        assertNotNull(property.getSquarerootPrice());
        assertNotNull(property.getCharacteristics());

        assertEquals(dto.getTitle(), property.getTitle());
        assertEquals(dto.getArea(), property.getArea());
        assertEquals(dto.getTotalArea(), property.getTotalArea());
        assertEquals(dto.getDescription(), property.getDescription());
        assertEquals(dto.getAddress(), property.getAddress());
        assertEquals(dto.getCondoPrice(), property.getCondoPrice());
        assertEquals(dto.getTaxPrice(), property.getTaxPrice());
        assertEquals(dto.getSquarerootPrice(), property.getSquarerootPrice());
        assertEquals(dto.getCharacteristics(), property.getCharacteristics());

    }

    @Test
    void shouldMapPropertyEntityToPropertyResponseDto(){
        List<String> charateristicsList = new ArrayList<>();
        charateristicsList.add("Pool");
        charateristicsList.add("Karaoke");

        BigDecimal condoPrice = new BigDecimal(2000);
        BigDecimal taxPrice = new BigDecimal(3000);
        BigDecimal sqrPrice = new BigDecimal(4000);

        Property property = new Property(
                10L,
                "OP1010",
                "Sample Title",
                20.5,
                21.5,
                5,
                4,
                3,
                2,
                "Sample Description",
                "Sample Address",
                condoPrice,
                taxPrice,
                sqrPrice,
                charateristicsList,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "UserCreatedBy",
                "UserModifiedBy"
        );

        PropertyResponseDto propertyResponseDto = PropertyMapper.toPropertyDto(property);

        assertNotNull(propertyResponseDto.getTitle());
        assertNotNull(propertyResponseDto.getArea());
        assertNotNull(propertyResponseDto.getTotalArea());
        assertNotNull(propertyResponseDto.getDescription());
        assertNotNull(propertyResponseDto.getAddress());
        assertNotNull(propertyResponseDto.getCondoPrice());
        assertNotNull(propertyResponseDto.getTaxPrice());
        assertNotNull(propertyResponseDto.getSquarerootPrice());
        assertNotNull(propertyResponseDto.getCharacteristics());

        assertNotNull(property.getTitle());
        assertNotNull(property.getArea());
        assertNotNull(property.getTotalArea());
        assertNotNull(property.getDescription());
        assertNotNull(property.getAddress());
        assertNotNull(property.getCondoPrice());
        assertNotNull(property.getTaxPrice());
        assertNotNull(property.getSquarerootPrice());
        assertNotNull(property.getCharacteristics());

        assertEquals(propertyResponseDto.getTitle(), property.getTitle());
        assertEquals(propertyResponseDto.getArea(), property.getArea());
        assertEquals(propertyResponseDto.getTotalArea(), property.getTotalArea());
        assertEquals(propertyResponseDto.getDescription(), property.getDescription());
        assertEquals(propertyResponseDto.getAddress(), property.getAddress());
        assertEquals(propertyResponseDto.getCondoPrice(), property.getCondoPrice());
        assertEquals(propertyResponseDto.getTaxPrice(), property.getTaxPrice());
        assertEquals(propertyResponseDto.getSquarerootPrice(), property.getSquarerootPrice());
        assertEquals(propertyResponseDto.getCharacteristics(), property.getCharacteristics());


    }

}