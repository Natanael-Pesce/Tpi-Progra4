package com.utn.frvm.prode_api.dtos.responcedto;

import java.time.LocalDateTime;
import java.util.List;

import com.utn.frvm.prode_api.utility.EstadoJornada;
import com.utn.frvm.prode_api.models.Partido;

import lombok.Data;

@Data
public class JornadaResponceDto {
    private String nombreJornada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Partido> partidos;
    private EstadoJornada estadoJornada = EstadoJornada.PROGRAMADA;
}
