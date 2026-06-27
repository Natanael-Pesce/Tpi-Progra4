package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EquipoRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 80)
    String nombre,

    Boolean estaActivo
) {}
