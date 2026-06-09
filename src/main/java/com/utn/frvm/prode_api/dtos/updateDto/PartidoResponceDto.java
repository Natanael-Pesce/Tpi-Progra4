package com.utn.frvm.prode_api.dtos.updateDto;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.models.Equipo;
import com.utn.frvm.prode_api.models.Jornada;
import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.utility.Resultado;

import lombok.Data;

@Data
public class PartidoResponceDto {
    
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private EstadoPartido estadoPartido;
    private Jornada jornada;
    private LocalDateTime horaInicio;
    private Resultado resultado;
}
