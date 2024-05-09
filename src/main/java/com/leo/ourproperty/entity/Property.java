package com.leo.ourproperty.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "property")
@EntityListeners(AuditingEntityListener.class)
public class Property implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_code", nullable = false, unique = true, length = 6)
    private String propertyCode;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "area", nullable = false)
    private Double area;

    @Column(name = "total_area", nullable = false)
    private Double totalArea;

    @Column(name = "num_bathrooms", nullable = false)
    private int numBathrooms;

    @Column(name = "num_bedrooms", nullable = false)
    private int numBedrooms;

    @Column(name = "num_suite", nullable = false)
    private int numSuite;

    @Column(name = "num_parking_spots", nullable = false)
    private int numParkingSpots;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "condo_price", nullable = false)
    private BigDecimal condoPrice;

    @Column(name = "tax_price", nullable = false)
    private BigDecimal taxPrice;

    @Column(name = "squareroot_price")
    private BigDecimal squarerootPrice;

    @Column(name = "characteristics")
    private List<String> characteristics;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "lastmodified_by")
    private String lastModifiedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(id, property.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
