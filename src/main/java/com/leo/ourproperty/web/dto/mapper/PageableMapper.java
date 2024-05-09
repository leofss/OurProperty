package com.leo.ourproperty.web.dto.mapper;

import com.leo.ourproperty.web.dto.PageableDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class PageableMapper {
    public static PageableDto pageableDto(Page page){
        return new ModelMapper().map(page, PageableDto.class);
    }
}
