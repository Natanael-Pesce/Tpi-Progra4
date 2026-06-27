package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FinalizarPartidoRequest(
    @NotNull(message = "Los goles del local son obligatorios")
    @Min(value = 0)
    Integer golesLocal,

    @NotNull(message = "Los goles del visitante son obligatorios")
    @Min(value = 0)
    Integer golesVisitante
) {}