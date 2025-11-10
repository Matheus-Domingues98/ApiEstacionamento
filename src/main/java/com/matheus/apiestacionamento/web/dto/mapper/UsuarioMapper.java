package com.matheus.apiestacionamento.web.dto.mapper;

import com.matheus.apiestacionamento.entities.Usuario;
import com.matheus.apiestacionamento.web.dto.UsuarioCreateDto;
import com.matheus.apiestacionamento.web.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto obj) {
        return  new ModelMapper().map(obj, Usuario.class);
    }

    public static UsuarioResponseDto toDto(Usuario obj) {
        String role = obj.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<>(){
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper =  new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(obj, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuario> obj) {
        return obj.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
