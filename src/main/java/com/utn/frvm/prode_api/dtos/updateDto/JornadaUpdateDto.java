package com.utn.frvm.prode_api.dtos.updateDto;

import java.time.LocalDateTime;
import java.util.List;

import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.utility.EstadoJornada;

import lombok.Data;

@Data
public class JornadaUpdateDto {
    private String nombreJornada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Partido> partidos;
    private EstadoJornada estadoJornada;
}
