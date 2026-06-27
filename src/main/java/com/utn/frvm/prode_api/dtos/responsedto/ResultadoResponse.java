package com.utn.frvm.prode_api.dtos.responsedto;

import com.utn.frvm.prode_api.utility.Resultado;
import java.time.LocalDateTime;

public record ResultadoResponse(
    Long            partidoId,
    EquipoResponse  equipoLocal,
    EquipoResponse  equipoVisitante,
    LocalDateTime   horaInicio,
    Integer         golesLocalOficial,
    Integer         golesVisitanteOficial,
    Resultado       resultadoFinal,
    Long            jornadaId,
    String          jornadaNombre,
    // Predicción propia (null si el usuario no pronosticó)
    Integer         miGolesLocal,
    Integer         miGolesVisitante,
    Resultado       miResultadoPronosticado,
    Integer         misPuntosObtenidos
) {}