package com.utn.frvm.prode_api.dtos.registrardto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRegistrarDto {
    
    @NotBlank(message = "El nombre es necesario")
    private String nombre;
    @NotBlank(message = "El apellido es necesario")
    private String apellido;
    @NotBlank(message = "El correo es necesario")
    private String correo;
    @NotBlank(message = "La contraseña es necesaria")
    private String contraseña;
}
