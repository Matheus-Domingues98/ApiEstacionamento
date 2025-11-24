package com.matheus.apiestacionamento.web.dto.mapper;

import com.matheus.apiestacionamento.entities.Vaga;
import com.matheus.apiestacionamento.web.dto.VagaCreateDto;
import com.matheus.apiestacionamento.web.dto.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper{

    public static Vaga toVaga (VagaCreateDto dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga obj) {
        return new ModelMapper().map(obj, VagaResponseDto.class);
    }
}
