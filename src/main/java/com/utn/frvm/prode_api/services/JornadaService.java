package com.utn.frvm.prode_api.services;

import org.springframework.stereotype.Service;
import java.util.List;
import com.utn.frvm.prode_api.mapper.JornadaMapper;
import com.utn.frvm.prode_api.repositories.JornadaRepository;
import com.utn.frvm.prode_api.dtos.registrardto.JornadaRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.JornadaResponseDto;
import com.utn.frvm.prode_api.models.Jornada;

@Service
public class JornadaService {

    private final JornadaRepository jornadaRepository;

    public JornadaService(JornadaRepository jornadaRepository){
        this.jornadaRepository = jornadaRepository;
    }

    public List<JornadaResponseDto> listarJornadas(){
        return jornadaRepository.findAll()
            .stream()
            .map(JornadaMapper::toDto)
            .toList();
    }

    public JornadaResponseDto buscarPorId(Integer id){
        Jornada jornada = jornadaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jornada no encontrada"));
        return JornadaMapper.toDto(jornada);
    }

    public JornadaResponseDto crearJornada(JornadaRegistrarDto dto){
        Jornada jornada = JornadaMapper.toEntity(dto);
        Jornada guardado = jornadaRepository.save(jornada);
        return JornadaMapper.toDto(guardado);
    }
}
