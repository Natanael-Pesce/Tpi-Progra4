package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearGrupoRequest(
    @NotBlank(message = "El nombre del grupo es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    String nombre,

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    String descripcion
) {}
