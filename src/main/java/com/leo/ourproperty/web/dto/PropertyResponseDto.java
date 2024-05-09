package com.leo.ourproperty.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponseDto {

    @JsonProperty("property_code")
    private String propertyCode;

    private String title;

    private Double area;

    @JsonProperty("total_area")
    private Double totalArea;

    @JsonProperty("num_bathrooms")
    private int numBathrooms;

    @JsonProperty("num_bedrooms")
    private int numBedrooms;

    @JsonProperty("num_suite")
    private int numSuite;

    @JsonProperty("num_parking_spots")
    private int numParkingSpots;

    private String description;

    private String address;

    @JsonProperty("condo_price")
    private BigDecimal condoPrice;

    @JsonProperty("tax_price")
    private BigDecimal taxPrice;

    @JsonProperty("squareroot_price")
    private BigDecimal squarerootPrice;

    private List<String> characteristics;
}
