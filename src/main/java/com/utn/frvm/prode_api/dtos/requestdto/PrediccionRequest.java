package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PrediccionRequest(
    @NotNull(message = "El ID del partido es obligatorio")
    Long partidoId,

    @NotNull(message = "Los goles del local son obligatorios")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    Integer golesLocal,

    @NotNull(message = "Los goles del visitante son obligatorios")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    Integer golesVisitante
) {}

