package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PartidoRequest(
    @NotNull(message = "El equipo local es obligatorio")
    Long equipoLocalId,

    @NotNull(message = "El equipo visitante es obligatorio")
    Long equipoVisitanteId,

    @NotNull(message = "La hora de inicio es obligatoria")
    LocalDateTime horaInicio,

    @NotNull(message = "La jornada es obligatoria")
    Long jornadaId
) {}
