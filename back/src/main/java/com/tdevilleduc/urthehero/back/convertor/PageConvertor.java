package com.tdevilleduc.urthehero.back.convertor;

import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.PageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageConvertor {

    @Autowired
    private ModelMapper modelMapper;

    public PageDTO convertToDto(Page page) {
        return modelMapper.map(page, PageDTO.class);
    }

    public Page convertToEntity(PageDTO pageDto) {
        return modelMapper.map(pageDto, Page.class);
    }
}
