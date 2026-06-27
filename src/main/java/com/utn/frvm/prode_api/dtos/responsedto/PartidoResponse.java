package com.utn.frvm.prode_api.dtos.responsedto;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.models.Equipo;
import com.utn.frvm.prode_api.models.Jornada;
import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.utility.Resultado;
import lombok.Data;

public record PartidoResponse(
    Long id,
    EquipoResponse equipoLocal,
    EquipoResponse equipoVisitante,
    LocalDateTime horaInicio,
    LocalDateTime cierrePrediccion,
    Integer golesLocal,
    Integer golesVisitante,
    EstadoPartido estadoPartido,
    Resultado resultadoFinal,
    Long jornadaId,
    String jornadaNombre,
    boolean prediccionBloqueada
) {}
