package com.leo.ourproperty.service;

import com.leo.ourproperty.entity.Property;
import com.leo.ourproperty.exception.EntityNotFoundExecption;
import com.leo.ourproperty.repository.PropertyRepository;
import com.leo.ourproperty.repository.projection.PropertyProjection;
import com.leo.ourproperty.web.dto.PropertyDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
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
}
