package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    String correo,

    @NotBlank(message = "La contraseña es obligatoria")
    String contrasena
) {}

