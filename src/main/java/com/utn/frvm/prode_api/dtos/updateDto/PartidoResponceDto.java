package com.utn.frvm.prode_api.dtos.updatedto;

import java.time.LocalDateTime;
import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.utility.Resultado;

import lombok.Data;

@Data
public class PartidoResponceDto {
    
    private int idEquipoLocal;
    private int idEquipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private EstadoPartido estadoPartido;
    private int idJornada;
    private LocalDateTime horaInicio;
    private Resultado resultado;
}
