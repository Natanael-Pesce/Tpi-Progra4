package com.utn.frvm.prode_api.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.utn.frvm.prode_api.dtos.registrardto.GrupoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.GrupoResponseDto;
import com.utn.frvm.prode_api.mapper.GrupoMapper;
import com.utn.frvm.prode_api.models.Grupo;
import com.utn.frvm.prode_api.repositories.GrupoRepository;

@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;

    public GrupoService(GrupoRepository grupoRepository){
        this.grupoRepository = grupoRepository;
    }

    public List<GrupoResponseDto> listarGrupos() {
        return grupoRepository.findAll()
            .stream()
            .map(GrupoMapper::toDto)
            .toList();
    }

    public GrupoResponseDto buscarPorId(Integer id) {
        Grupo grupo = grupoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        return GrupoMapper.toDto(grupo);
    }

    public GrupoResponseDto crear(GrupoRegistrarDto dto){
        Grupo grupo = GrupoMapper.toEntity(dto);
        Grupo guardado = grupoRepository.save(grupo);
        return GrupoMapper.toDto(guardado);
    }
}
