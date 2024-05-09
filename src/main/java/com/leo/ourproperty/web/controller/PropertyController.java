package com.leo.ourproperty.web.controller;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.repository.projection.PropertyProjection;
import com.leo.ourproperty.service.PropertyService;
import com.leo.ourproperty.web.dto.PageableDto;
import com.leo.ourproperty.web.dto.PropertyDto;
import com.leo.ourproperty.web.dto.PropertyResponseDto;
import com.leo.ourproperty.web.dto.mapper.PageableMapper;
import com.leo.ourproperty.web.dto.mapper.PropertyMapper;
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
@RequestMapping("api/v1/property")
public class PropertyController {
    private final PropertyService propertyService;
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTATION')")
    public ResponseEntity<PropertyResponseDto> create(@RequestBody @Valid PropertyDto dto){
        Property property = PropertyMapper.toPropertyEntity(dto);
        propertyService.create(property);
        return ResponseEntity.status(201).body(PropertyMapper.toPropertyDto(property));

    }

    @GetMapping
    public ResponseEntity<PageableDto> getAll(Pageable pageable){
        Page<PropertyProjection> property = propertyService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.pageableDto(property));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTATION')")
    public void delete(@PathVariable Long id){
        propertyService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTATION')")
    public ResponseEntity<PropertyResponseDto> edit(@PathVariable Long id, @Valid @RequestBody PropertyDto propertyDto){
        Property property = propertyService.edit(id, propertyDto);
        return ResponseEntity.ok().body(PropertyMapper.toPropertyDto(property));
    }
}
