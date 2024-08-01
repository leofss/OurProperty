package com.leo.ourproperty.repository.search;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SearchParamsBuilder {
    public static Map<String, Object> createSearchParams(Object... params) {
        String[] paramNames = {
                "property_code", "title", "area", "total_area", "num_bathrooms",
                "num_bedrooms", "num_suite", "num_parking_spots", "tax_price",
                "description", "address", "condo_price", "squareroot_price",
                "characteristics"
        };

        Map<String, Object> searchParams = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            searchParams.put(paramNames[i], params[i]);
        }
        return searchParams;
    }
}
