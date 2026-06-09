package com.utn.frvm.prode_api.dtos.responcedto;

import lombok.Data;

@Data
public class EquipoResponceDto {
    
    private int idEquipo;

    private String nombreEquipo;
    private boolean estaActivo;
}
