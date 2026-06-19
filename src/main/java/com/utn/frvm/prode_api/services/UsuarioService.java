package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.registrardto.UsuarioRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.UsuarioResponseDto;
import com.utn.frvm.prode_api.mapper.UsuarioMapper;
import com.utn.frvm.prode_api.models.Usuario;
import com.utn.frvm.prode_api.repositories.UsuarioRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    
    public List<UsuarioResponseDto> listarUsuarios() {
        return usuarioRepository.findAll()
            .stream()
            .map(UsuarioMapper::toDto)
            .toList();
    }

    public UsuarioResponseDto obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UsuarioMapper.toDto(usuario);
    }

    public UsuarioResponseDto crearUsuario(UsuarioRegistrarDto dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioMapper.toDto(guardado);
    }
}
