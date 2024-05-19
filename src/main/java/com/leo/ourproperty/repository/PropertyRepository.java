package com.leo.ourproperty.repository;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.repository.projection.PropertyProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    boolean existsByPropertyCodeLike(@NonNull String propertyCode);

    @Query("select c from Property c")
    Page<PropertyProjection> findAllPageable(Pageable pageable);

    @Query("SELECT MAX(e.area) FROM Property e")
    Double findMaxAreaValue();

    @Query("SELECT MAX(e.totalArea) FROM Property e")
    Double findMaxTotalAreaValue();

    @Query("SELECT MAX(e.numBathrooms) FROM Property e")
    Integer findMaxNumBathrooms();

    @Query("SELECT MAX(e.numBedrooms) FROM Property e")
    Integer findMaxNumBedrooms();

    @Query("SELECT MAX(e.numSuite) FROM Property e")
    Integer findMaxNumSuite();

    @Query("SELECT MAX(e.numParkingSpots) FROM Property e")
    Integer findMaxParkingSpots();

    @Query("SELECT MAX(e.taxPrice) FROM Property e")
    BigDecimal findMaxTaxPrice();
}
