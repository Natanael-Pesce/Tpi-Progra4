package com.utn.frvm.prode_api.dtos.responsedto;

import com.utn.frvm.prode_api.utility.EstadoJornada;
import java.time.LocalDate;

public record JornadaResponse(
    Long id,
    String nombre,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    EstadoJornada estadoJornada,
    int totalPartidos
) {}

