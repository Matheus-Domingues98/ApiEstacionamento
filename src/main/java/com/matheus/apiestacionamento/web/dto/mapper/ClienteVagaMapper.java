package com.matheus.apiestacionamento.web.dto.mapper;

import com.matheus.apiestacionamento.entities.ClienteVaga;
import com.matheus.apiestacionamento.web.dto.EstacionamentoCreateDto;
import com.matheus.apiestacionamento.web.dto.EstacionamentoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

     public static ClienteVaga toClienteVaga(EstacionamentoCreateDto obj) {
         return new ModelMapper().map(obj, ClienteVaga.class);
     }

     public static EstacionamentoResponseDto toDto(ClienteVaga obj) {
         return new ModelMapper().map(obj, EstacionamentoResponseDto.class);
     }
}
