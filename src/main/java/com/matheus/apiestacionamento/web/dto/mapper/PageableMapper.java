package com.matheus.apiestacionamento.web.dto.mapper;

import com.matheus.apiestacionamento.web.dto.PageableDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDto toDto(Page page) {

        return new ModelMapper().map(page, PageableDto.class);
    }
}
