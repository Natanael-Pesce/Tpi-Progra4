package com.utn.frvm.prode_api.dtos.updateDto;

import lombok.Data;

@Data
public class UsuarioUpdateDto {
    
    private String nombre;
    private String apellido;
    private String correo;
    private int puntos;
}
