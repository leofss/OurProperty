package com.leo.ourproperty.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {

    @NotBlank
    private String title;

    @NotNull
    @PositiveOrZero
    private Double area;

    @NotNull
    @PositiveOrZero
    @JsonProperty("total_area")
    private Double totalArea;

    @NotNull
    @PositiveOrZero
    @JsonProperty("num_bathrooms")
    private int numBathrooms;

    @NotNull
    @PositiveOrZero
    @JsonProperty("num_bedrooms")
    private int numBedrooms;

    @NotNull
    @PositiveOrZero
    @JsonProperty("num_suite")
    private int numSuite;

    @NotNull
    @PositiveOrZero
    @JsonProperty("num_parking_spots")
    private int numParkingSpots;

    @NotBlank
    private String description;

    @NotBlank
    private String address;

    @NotNull
    @PositiveOrZero
    @JsonProperty("condo_price")
    private BigDecimal condoPrice;

    @NotNull
    @PositiveOrZero
    @JsonProperty("tax_price")
    private BigDecimal taxPrice;

    @NotNull
    @PositiveOrZero
    @JsonProperty("squareroot_price")
    private BigDecimal squarerootPrice;

    @NotNull
    @Size(min = 1)
    private List<String> characteristics;

}
