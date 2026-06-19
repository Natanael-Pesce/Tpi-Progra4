package com.utn.frvm.prode_api.services;

import org.springframework.stereotype.Service;
import java.util.List;
import com.utn.frvm.prode_api.dtos.registrardto.PartidoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.PartidoResponseDto;
import com.utn.frvm.prode_api.mapper.PartidoMapper;
import com.utn.frvm.prode_api.repositories.PartidoRepository;
import com.utn.frvm.prode_api.models.Partido;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;

    public PartidoService(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }

    public List<PartidoResponseDto> listarPartidos(){
        return partidoRepository.findAll()
            .stream()
            .map(PartidoMapper::toDto)
            .toList();
    }

    public PartidoResponseDto obtenerPorId(Integer id) {
        Partido partido = partidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));
        return PartidoMapper.toDto(partido);
    }

    public PartidoResponseDto crearPartido(PartidoRegistrarDto dto) {
        Partido partido = PartidoMapper.toEntity(dto);
        Partido guardado = partidoRepository.save(partido);
        return PartidoMapper.toDto(guardado);
    }
}
