package com.leo.ourproperty.repository;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.repository.projection.PropertyProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    boolean existsByPropertyCodeLike(@NonNull String propertyCode);

    @Query("select c from Property c")
    Page<PropertyProjection> findAllPageable(Pageable pageable);

}
