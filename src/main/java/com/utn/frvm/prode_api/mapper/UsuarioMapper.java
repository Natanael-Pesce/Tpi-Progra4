package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responcedto.UsuarioResponceDto;
import com.utn.frvm.prode_api.models.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioResponceDto dto){
        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(dto.getRol());

        return usuario;
    }

    public static UsuarioResponceDto toDto(Usuario usuario){

        UsuarioResponceDto dto = new UsuarioResponceDto();

        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());

        return dto;
    }   
}