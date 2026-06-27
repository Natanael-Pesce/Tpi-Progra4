package com.utn.frvm.prode_api.dtos.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50)
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50)
    String apellido,

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    String correo,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = ".*[A-Z].*", message = "La contraseña debe contener al menos una mayúscula")
    @Pattern(regexp = ".*[0-9].*", message = "La contraseña debe contener al menos un número")
    String contrasena
) {}
