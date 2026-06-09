package com.utn.frvm.prode_api.dtos.logindto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UsuarioLoginDto {
    
    private String nombre;
    private String apellido;

    @Email
    private String correo;
    private String contraseña;
}
