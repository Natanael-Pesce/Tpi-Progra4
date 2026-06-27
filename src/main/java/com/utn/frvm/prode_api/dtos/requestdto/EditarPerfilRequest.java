package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditarPerfilRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50)
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50)
    String apellido,

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    String correo
) {}