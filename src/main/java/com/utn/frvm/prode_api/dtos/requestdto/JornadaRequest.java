package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record JornadaRequest(
    @NotBlank(message = "El nombre de la jornada es obligatorio")
    String nombre,

    @NotNull(message = "La fecha de inicio es obligatoria")
    LocalDate fechaInicio,

    @NotNull(message = "La fecha de fin es obligatoria")
    LocalDate fechaFin
) {}
