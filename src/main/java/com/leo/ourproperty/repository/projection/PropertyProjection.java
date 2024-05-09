package com.leo.ourproperty.repository.projection;

import java.math.BigDecimal;
import java.util.List;

public interface PropertyProjection {
     String getPropertyCode();

     String getTitle();

     Double getArea();

     Double getTotalArea();

     int getNumBathrooms();

     int getNumBedrooms();

     int getNumSuite();

     int getNumParkingSpots();

     String getDescription();

     String getAddress();

     BigDecimal getCondoPrice();

     BigDecimal getTaxPrice();

     BigDecimal getSquarerootPrice();

     List<String> getCharacteristics();
}
