package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.registrardto.EquipoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.EquipoResponseDto;
import com.utn.frvm.prode_api.mapper.EquipoMapper;
import com.utn.frvm.prode_api.models.Equipo;
import com.utn.frvm.prode_api.repositories.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {
    private final EquipoRepository equipoRepository;
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }
public List<EquipoResponseDto> listarEquipos() {
        return equipoRepository.findAll()
                .stream()
                .map(EquipoMapper::toDto)
                .toList();
}
public EquipoResponseDto buscarPorId(Integer id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return EquipoMapper.toDto(equipo);
}
public EquipoResponseDto crear(EquipoRegistrarDto dto){
        Equipo equipo = EquipoMapper.toEntity(dto);
        Equipo guardado = equipoRepository.save(equipo);
        return  EquipoMapper.toDto(guardado);
}
}
