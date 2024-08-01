package com.leo.ourproperty.web.controller;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.service.PropertyService;
import com.leo.ourproperty.repository.search.SearchParamsBuilder;
import com.leo.ourproperty.web.api.PropertyAPI;
import com.leo.ourproperty.web.dto.PageableDto;
import com.leo.ourproperty.web.dto.PropertyDto;
import com.leo.ourproperty.web.dto.PropertyResponseDto;
import com.leo.ourproperty.web.dto.mapper.PageableMapper;
import com.leo.ourproperty.web.dto.mapper.PropertyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class PropertyController implements PropertyAPI {
    private final PropertyService propertyService;

    @Override
    public ResponseEntity<PropertyResponseDto> create(final PropertyDto dto) {
        Property property = PropertyMapper.toPropertyEntity(dto);
        propertyService.create(property);
        return ResponseEntity.status(201).body(PropertyMapper.toPropertyDto(property));
    }

    @Override
    public ResponseEntity<PageableDto> getAll(final Pageable pageable) {
        Page<PropertyResponseDto> property = propertyService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.pageableDto(property));
    }

    @Override
    public void delete(final Long id) {
        propertyService.delete(id);
    }

    @Override
    public ResponseEntity<PropertyResponseDto> edit(final Long id, final PropertyDto propertyDto) {
        Property property = propertyService.edit(id, propertyDto);
        return ResponseEntity.ok().body(PropertyMapper.toPropertyDto(property));
    }

    @Override
    public ResponseEntity<PageableDto> search(final String propertyCode, final String title, final Double area,
            final Double totalArea, final Integer numBathrooms, final Integer numBedrooms, final Integer numSuite,
            final Integer numParkingSpots, final BigDecimal taxPrice, final String description, final String address,
            final BigDecimal condoPrice, final BigDecimal squarerootPrice, final List<String> characteristics,
            final Pageable pageable) {

        Map<String, Object> searchParams = SearchParamsBuilder.createSearchParams(
                propertyCode, title, area, totalArea, numBathrooms, numBedrooms, numSuite,
                numParkingSpots, taxPrice, description, address, condoPrice, squarerootPrice,
                characteristics
        );

        Page<PropertyResponseDto> page = propertyService.search(searchParams, pageable);
        return ResponseEntity.ok(PageableMapper.pageableDto(page));

    }

}
