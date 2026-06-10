package com.utn.frvm.prode_api.dtos.responsedto;

import lombok.Data;

@Data
public class EquipoResponseDto {
    private int idEquipo;
    private String nombreEquipo;
    private boolean estaActivo;
}
