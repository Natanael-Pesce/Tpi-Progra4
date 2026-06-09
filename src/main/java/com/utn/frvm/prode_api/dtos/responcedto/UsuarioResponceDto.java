package com.utn.frvm.prode_api.dtos.responcedto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UsuarioResponceDto {
    
    private String nombre;
    private String apellido;

    @Email
    private String correo;
    //private List<Prediccion> predicciones;
    private int puntos;
}
