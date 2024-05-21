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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/property")
@Slf4j
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CAPTATION')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CAPTATION')")
    public void delete(@PathVariable Long id){
        propertyService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CAPTATION')")
    public ResponseEntity<PropertyResponseDto> edit(@PathVariable Long id, @Valid @RequestBody PropertyDto propertyDto){
        Property property = propertyService.edit(id, propertyDto);
        return ResponseEntity.ok().body(PropertyMapper.toPropertyDto(property));
    }

    @GetMapping("/search")
    public ResponseEntity<PageableDto> search(
            @RequestParam(required = false, name = "property_code") String propertyCode,
            @RequestParam(required = false, name = "title") String title,
            @RequestParam(required = false, name = "area") Double area,
            @RequestParam(required = false, name = "total_area") Double totalArea,
            @RequestParam(required = false, name = "num_bathrooms") Integer numBathrooms,
            @RequestParam(required = false, name = "num_bedrooms") Integer numBedrooms,
            @RequestParam(required = false, name = "num_suite") Integer numSuite,
            @RequestParam(required = false, name = "num_parking_spots") Integer numParkingSpots,
            @RequestParam(required = false, name = "tax_price") BigDecimal taxPrice,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "address") String address,
            @RequestParam(required = false, name = "condo_price") BigDecimal condoPrice,
            @RequestParam(required = false, name = "squareroot_price") BigDecimal squarerootPrice,
            @RequestParam(required = false, name = "characteristics") List<String> characteristics,
            Pageable pageable){

        HashMap<String, Object> searchParams = new HashMap<>();
        searchParams.put("property_code", propertyCode);
        searchParams.put("title", title);
        searchParams.put("area", area);
        searchParams.put("total_area", totalArea);
        searchParams.put("num_bathrooms", numBathrooms);
        searchParams.put("num_bedrooms", numBedrooms);
        searchParams.put("num_suite", numSuite);
        searchParams.put("num_parking_spots", numParkingSpots);
        searchParams.put("tax_price", taxPrice);
        searchParams.put("description", description);
        searchParams.put("address", address);
        searchParams.put("condo_price", condoPrice);
        searchParams.put("squareroot_price", squarerootPrice);
        searchParams.put("characteristics", characteristics);

        Page<PropertyResponseDto> page = propertyService.search(searchParams, pageable);
        return ResponseEntity.ok(PageableMapper.pageableDto(page));

    }

}
