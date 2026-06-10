package com.utn.frvm.prode_api.dtos.responsedto;

import java.time.LocalDateTime;
import java.util.List;

import com.utn.frvm.prode_api.utility.EstadoJornada;
import com.utn.frvm.prode_api.models.Partido;

import lombok.Data;

@Data
public class JornadaResponseDto {
    private int idJornada;
    private String nombreJornada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Partido> partidos;
    private EstadoJornada estadoJornada;
    private boolean estaActivo;
}
