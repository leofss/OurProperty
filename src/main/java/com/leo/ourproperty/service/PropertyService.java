package com.leo.ourproperty.service;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.exception.EntityNotFoundExecption;
import com.leo.ourproperty.repository.PropertyRepository;
import com.leo.ourproperty.repository.projection.PropertyProjection;
import com.leo.ourproperty.web.dto.PropertyDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final EntityManager entityManager;
    private static final Random random = new Random();

    public String generatePropertyCode(){
        String code = String.format("OP%04d", random.nextInt(10000));
        if(checkIfCodeExists(code)){
            return generatePropertyCode();
        }
        return code;
    }
    public boolean checkIfCodeExists(String code){
        return propertyRepository.existsByPropertyCodeLike(code);
    }
    @Transactional
    public Property create(Property property){
        String propertyCode = generatePropertyCode();
        property.setPropertyCode(propertyCode);
        return propertyRepository.save(property);
    }

    @Transactional(readOnly = true)
    public Page<PropertyProjection> findAll(Pageable pageable){
        return propertyRepository.findAllPageable(pageable);
    }

    @Transactional
    public void delete(Long id){
        if(!propertyRepository.existsById(id)){
            throw new EntityNotFoundExecption("Property with id " + id + " not found");
        }
        propertyRepository.deleteById(id);
    }

    @Transactional
    public Property edit(Long id, PropertyDto propertyDto){
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundExecption("Property with id " + id + " not found"));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(propertyDto, property);
        return propertyRepository.save(property);
    }

    @Transactional
    public Page<Property> search(Map<String, Object> searchParams, Pageable pageable){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
        List<Predicate> predicates = new ArrayList<>();

        //select * from property
        Root<Property> root = criteriaQuery.from(Property.class);

        String propertyCode = searchParams.get("property_code").toString();
        if(propertyCode != null){
            Predicate propertyCodePred = criteriaBuilder
                    .like(root.get("propertyCode"), "%" + propertyCode + "%");
            predicates.add(propertyCodePred);
        }

        String title = searchParams.get("title").toString();
        if(title != null){
            Predicate titlePred = criteriaBuilder
                    .like(root.get("title"), "%" + title + "%");
            predicates.add(titlePred);
        }

        Double areaValue = (Double) searchParams.get("area");
        Double maxValue = propertyRepository.findMaxAreaValue();
        if (areaValue != null) {
            Predicate areaPred = criteriaBuilder
                    .between(root.get("area"), areaValue, maxValue);
            predicates.add(areaPred);
        }

        Double totalAreaValue = (Double) searchParams.get("total_area");
        Double maxTotalAreaValue = propertyRepository.findMaxTotalAreaValue();
        if(totalAreaValue != null){
            Predicate totalAreaPred = criteriaBuilder
                    .between(root.get("totalArea"), totalAreaValue, maxTotalAreaValue);
            predicates.add(totalAreaPred);
        }

        Integer numBathroomsValue = (Integer) searchParams.get("num_bathrooms");
        Integer maxNumBathroomsValue = propertyRepository.findMaxNumBathrooms();
        if(numBathroomsValue != null){
            Predicate numBathroomsPred = criteriaBuilder
                    .between(root.get("numBathrooms"), numBathroomsValue, maxNumBathroomsValue);
            predicates.add(numBathroomsPred);
        }

        Integer numBedroomsValue = (Integer) searchParams.get("num_bedrooms");
        Integer maxNumBedroomsValue = propertyRepository.findMaxNumBedrooms();
        if(numBedroomsValue != null){
            Predicate numBedroomsPred = criteriaBuilder
                    .between(root.get("numBathrooms"), numBedroomsValue, maxNumBedroomsValue);
            predicates.add(numBedroomsPred);
        }

        Integer numSuite = (Integer) searchParams.get("num_suite");
        Integer maxNumSuite = propertyRepository.findMaxNumSuite();
        if(numSuite != null){
            Predicate numSuitePred = criteriaBuilder
                    .between(root.get("numSuite"), numSuite, maxNumSuite);
            predicates.add(numSuitePred);
        }

        Integer numParkingSpots = (Integer) searchParams.get("num_parking_spots");
        Integer maxParkingSpots = propertyRepository.findMaxParkingSpots();
        if(numParkingSpots != null){
            Predicate numParkingSpotsPred = criteriaBuilder
                    .between(root.get("numParkingSpots"), numParkingSpots, maxParkingSpots);
            predicates.add(numParkingSpotsPred);
        }

        BigDecimal taxPrice = (BigDecimal) searchParams.get("tax_price");
        if (taxPrice != null) {
            Predicate greaterThanOrEqualToTaxPrice = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("taxPrice"), taxPrice);

            Predicate taxPricePred = criteriaBuilder.and(greaterThanOrEqualToTaxPrice);
            predicates.add(taxPricePred);
        }

        String description = searchParams.get("description").toString();
        if(description != null){
            Predicate descriptionPred = criteriaBuilder
                    .like(root.get("description"), "%" + description + "%");
            predicates.add(descriptionPred);
        }

        String address = searchParams.get("address").toString();
        if(address != null){
            Predicate addressPred = criteriaBuilder
                    .like(root.get("address"), "%" + address + "%");
            predicates.add(addressPred);
        }

        BigDecimal condoPrice = (BigDecimal) searchParams.get("condo_price");
        if (condoPrice != null) {
            Predicate greaterThanOrEqualToCondoPrice = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("condoPrice"), condoPrice);

            Predicate condoPricePred = criteriaBuilder.and(greaterThanOrEqualToCondoPrice);
            predicates.add(condoPricePred);
        }

        BigDecimal squarerootPrice = (BigDecimal) searchParams.get("squareroot_price");
        if (squarerootPrice != null) {
            Predicate greaterThanOrEqualToCondoPrice = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("squarerootPrice"), squarerootPrice);

            Predicate squarerootPricePred = criteriaBuilder.and(greaterThanOrEqualToCondoPrice);
            predicates.add(squarerootPricePred);
        }

        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<Property> finalQuery = entityManager.createQuery(criteriaQuery);

        List<Property> results = finalQuery.getResultList();

        List<String> characteristics = (List<String>) searchParams.get("characteristics");

        if(!characteristics.isEmpty()){
            results = results.stream()
                    .filter(property -> property.getCharacteristics().stream()
                            .anyMatch(characteristics::contains))
                    .collect(Collectors.toList());
            return new PageImpl<>(results, pageable, results.size());
        }

        return new PageImpl<>(results, pageable, results.size());
    }
}
