package com.leo.ourproperty.service;


import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.repository.PropertyRepository;
import com.leo.ourproperty.repository.projection.PropertyProjection;
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
import org.springframework.data.domain.Page;
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

    @Test
    void shouldFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        PropertyProjection propertyProjection = new PropertyProjection() {
            @Override
            public String getPropertyCode() {
                return "OP0000";
            }

            @Override
            public String getTitle() {
                return "Test Property";
            }

            @Override
            public Double getArea() {
                return 120.0;
            }

            @Override
            public Double getTotalArea() {
                return 200.0;
            }

            @Override
            public int getNumBathrooms() {
                return 2;
            }

            @Override
            public int getNumBedrooms() {
                return 3;
            }

            @Override
            public int getNumSuite() {
                return 1;
            }

            @Override
            public int getNumParkingSpots() {
                return 2;
            }

            @Override
            public String getDescription() {
                return "A beautiful property";
            }

            @Override
            public String getAddress() {
                return "123 Test Street";
            }

            @Override
            public BigDecimal getCondoPrice() {
                return new BigDecimal("250.00");
            }

            @Override
            public BigDecimal getTaxPrice() {
                return new BigDecimal("100.00");
            }

            @Override
            public BigDecimal getSquarerootPrice() {
                return new BigDecimal("3000.00");
            }

            @Override
            public List<String> getCharacteristics() {
                return Collections.singletonList("Characteristic 1");
            }
        };
        Page<PropertyProjection> propertyProjections = new PageImpl<>(Collections.singletonList(propertyProjection));

        when(propertyRepository.findAllPageable(pageable)).thenReturn(propertyProjections);

        Page<PropertyResponseDto> result = propertyService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        PropertyResponseDto responseDto = result.getContent().get(0);
        assertEquals(propertyProjection.getPropertyCode(), responseDto.getPropertyCode());
        assertEquals(propertyProjection.getTitle(), responseDto.getTitle());
        assertEquals(propertyProjection.getArea(), responseDto.getArea());
        assertEquals(propertyProjection.getTotalArea(), responseDto.getTotalArea());
        assertEquals(propertyProjection.getNumBathrooms(), responseDto.getNumBathrooms());
        assertEquals(propertyProjection.getNumBedrooms(), responseDto.getNumBedrooms());
        assertEquals(propertyProjection.getNumSuite(), responseDto.getNumSuite());
        assertEquals(propertyProjection.getNumParkingSpots(), responseDto.getNumParkingSpots());
        assertEquals(propertyProjection.getDescription(), responseDto.getDescription());
        assertEquals(propertyProjection.getAddress(), responseDto.getAddress());
        assertEquals(propertyProjection.getCondoPrice(), responseDto.getCondoPrice());
        assertEquals(propertyProjection.getTaxPrice(), responseDto.getTaxPrice());
        assertEquals(propertyProjection.getSquarerootPrice(), responseDto.getSquarerootPrice());
        assertEquals(propertyProjection.getCharacteristics(), responseDto.getCharacteristics());

        verify(propertyRepository, times(1)).findAllPageable(pageable);
    }


}