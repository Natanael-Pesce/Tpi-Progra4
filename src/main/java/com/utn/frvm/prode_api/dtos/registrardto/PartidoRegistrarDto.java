package com.utn.frvm.prode_api.dtos.registrardto;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.models.Equipo;
import com.utn.frvm.prode_api.models.Jornada;

import lombok.Data;

@Data
public class PartidoRegistrarDto {
    
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Jornada jornada;
    private LocalDateTime fechaInicio;
}
