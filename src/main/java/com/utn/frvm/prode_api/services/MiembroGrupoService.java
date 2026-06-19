package com.utn.frvm.prode_api.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.utn.frvm.prode_api.dtos.registrardto.MiembroGrupoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.MiembroGrupoResponseDto;
import com.utn.frvm.prode_api.mapper.MiembroMapper;
import com.utn.frvm.prode_api.models.MiembroGrupo;
import com.utn.frvm.prode_api.repositories.MiembroGrupoRepository;

@Service
public class MiembroGrupoService {

    private final MiembroGrupoRepository miembroGrupoRepository;

    public MiembroGrupoService(MiembroGrupoRepository miembroGrupoRepository){
        this.miembroGrupoRepository = miembroGrupoRepository;
    }

    public List<MiembroGrupoResponseDto> listarMiembros() {
        return miembroGrupoRepository.findAll()
            .stream()
            .map(MiembroMapper::toDto)
            .toList();
    }

    public MiembroGrupoResponseDto buscarPorId(Integer id) {
        MiembroGrupo miembroGrupo = miembroGrupoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
        return MiembroMapper.toDto(miembroGrupo);
    }

    public MiembroGrupoResponseDto crearMiembro(MiembroGrupoRegistrarDto dto) {
        MiembroGrupo miembroGrupo = MiembroMapper.toEntity(dto);
        MiembroGrupo guardado = miembroGrupoRepository.save(miembroGrupo);
        return MiembroMapper.toDto(guardado);
    }
}
