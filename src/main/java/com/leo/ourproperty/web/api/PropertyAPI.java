package com.leo.ourproperty.web.api;

import com.leo.ourproperty.web.dto.PageableDto;
import com.leo.ourproperty.web.dto.PropertyDto;
import com.leo.ourproperty.web.dto.PropertyResponseDto;
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

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("api/v1/property")
@Tag(name = "V1 - Property")
public interface PropertyAPI {
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CAPTATION')")
    @Operation(summary = "Create property", description = "Available only  to users with ADMIN and CAPTATION roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Property sucessfully created"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity"),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
    })
    ResponseEntity<PropertyResponseDto> create(@RequestBody @Valid PropertyDto dto);

    @GetMapping
    @Operation(summary = "Get all properties", description = "Available for all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Properties sucessfully listed"),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
    })
    ResponseEntity<PageableDto> getAll(Pageable pageable);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CAPTATION')")
    @Operation(summary = "Delete single property", description = "Available only for users with ADMIN and CAPTATION roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Property sucessfully deleted"),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
            @ApiResponse(responseCode = "404", description = "Property not found"),

    })
    void delete(@PathVariable Long id);

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CAPTATION')")
    @Operation(summary = "Edit single property", description = "Available only for users with ADMIN and CAPTATION roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Property sucessfully edited"),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
            @ApiResponse(responseCode = "404", description = "Property not found"),

    })
    ResponseEntity<PropertyResponseDto> edit(@PathVariable Long id, @Valid @RequestBody PropertyDto propertyDto);

    @GetMapping("/search")
    @Operation(summary = "Search and filter all properties", description = "Available for all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Properties sucessfully filtered"),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
    })
    ResponseEntity<PageableDto> search(
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
            Pageable pageable);
}
