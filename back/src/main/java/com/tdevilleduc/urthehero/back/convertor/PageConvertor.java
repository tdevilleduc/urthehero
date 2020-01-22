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
        PageDTO pageDto = modelMapper.map(page, PageDTO.class);
        return pageDto;
    }

    public Page convertToEntity(PageDTO pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        return page;
    }
}
