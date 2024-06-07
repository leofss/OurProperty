package com.leo.ourproperty.service;


import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.repository.PropertyRepository;
import com.leo.ourproperty.web.dto.PropertyDto;
import com.leo.ourproperty.web.dto.PropertyResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PropertyServiceTest {

    @InjectMocks
    PropertyService propertyService;

    @Mock
    PropertyRepository propertyRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Property> criteriaQuery;

    @Mock
    private Root<Property> root;

    @Mock
    private TypedQuery<Property> typedQuery;

    private List<Property> propertyList;


    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        propertyList = new ArrayList<>();
        Property property = initializeProperty();
        propertyList.add(property);
    }

    private Property initializeProperty(){
        List<String> charateristicsList = new ArrayList<>();
        charateristicsList.add("Pool");
        charateristicsList.add("Karaoke");

        BigDecimal condoPrice = new BigDecimal(2000);
        BigDecimal taxPrice = new BigDecimal(3000);
        BigDecimal sqrPrice = new BigDecimal(4000);

        return new Property(
                10L,
                "OP1010",
                "Sample Title",
                20.5,
                21.5,
                5,
                4,
                3,
                2,
                "Sample Description",
                "Sample Address",
                condoPrice,
                taxPrice,
                sqrPrice,
                charateristicsList,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "UserCreatedBy",
                "UserModifiedBy"
        );
    }
    @Test
    void shouldCreatePropertySuccessfully(){

        Property property = initializeProperty();

        when(propertyRepository.save(any(Property.class))).thenReturn(property);


        Property propertyCreated = propertyService.create(property);


        assertNotNull(propertyCreated);
        assertEquals(propertyCreated.getTitle(), property.getTitle());
        assertEquals(propertyCreated.getArea(), property.getArea());
        assertEquals(propertyCreated.getTotalArea(), property.getTotalArea());
        assertEquals(propertyCreated.getDescription(), property.getDescription());
        assertEquals(propertyCreated.getAddress(), property.getAddress());
        assertEquals(propertyCreated.getCondoPrice(), property.getCondoPrice());
        assertEquals(propertyCreated.getTaxPrice(), property.getTaxPrice());
        assertEquals(propertyCreated.getSquarerootPrice(), property.getSquarerootPrice());
        assertEquals(propertyCreated.getCharacteristics(), property.getCharacteristics());
    }

    @Test
    void shouldEditPropertySuccessfully(){
        Property property = initializeProperty();

        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setTitle("Updated Title");
        propertyDto.setArea(30.0);
        propertyDto.setTotalArea(40.0);

        when(propertyRepository.findById(anyLong())).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        Property updatedProperty = propertyService.edit(property.getId(), propertyDto);

        assertNotNull(updatedProperty);
        assertEquals("Updated Title", updatedProperty.getTitle());
        assertEquals(30.0, updatedProperty.getArea());
        assertEquals(40.0, updatedProperty.getTotalArea());
    }

    @Test
    void shouldSearchPropertiesSuccessfully() {
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("property_code", "OP1010");
        searchParams.put("title", "Sample Title");
        searchParams.put("area", 20.5);
        searchParams.put("total_area", 21.5);
        searchParams.put("num_bathrooms", 2);
        searchParams.put("num_bedrooms", 3);
        searchParams.put("num_suite", 1);
        searchParams.put("num_parking_spots", 2);
        searchParams.put("tax_price", new BigDecimal(3000));
        searchParams.put("description", "Sample Description");
        searchParams.put("address", "Sample Address");
        searchParams.put("condo_price", new BigDecimal(2000));
        searchParams.put("squareroot_price", new BigDecimal(4000));
        searchParams.put("characteristics", Arrays.asList("Pool", "Karaoke"));

        Pageable pageable = PageRequest.of(0, 10);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Property.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Property.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(propertyList);

        when(propertyRepository.findMaxAreaValue()).thenReturn(50.0);
        when(propertyRepository.findMaxTotalAreaValue()).thenReturn(60.0);
        when(propertyRepository.findMaxNumBathrooms()).thenReturn(3);
        when(propertyRepository.findMaxNumBedrooms()).thenReturn(4);
        when(propertyRepository.findMaxNumSuite()).thenReturn(2);
        when(propertyRepository.findMaxParkingSpots()).thenReturn(3);

        PageImpl<PropertyResponseDto> result = propertyService.search(searchParams, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(Property.class);
        verify(criteriaQuery).from(Property.class);
        verify(entityManager).createQuery(criteriaQuery);
        verify(typedQuery).getResultList();
        verify(propertyRepository).findMaxAreaValue();
        verify(propertyRepository).findMaxTotalAreaValue();
        verify(propertyRepository).findMaxNumBathrooms();
        verify(propertyRepository).findMaxNumBedrooms();
        verify(propertyRepository).findMaxNumSuite();
        verify(propertyRepository).findMaxParkingSpots();
    }

    @Test
    void shouldDeletePropertySuccessfully() {
        Long propertyId = 1L;
        when(propertyRepository.existsById(propertyId)).thenReturn(true);

        propertyService.delete(propertyId);

        verify(propertyRepository, times(1)).deleteById(propertyId);
    }


}