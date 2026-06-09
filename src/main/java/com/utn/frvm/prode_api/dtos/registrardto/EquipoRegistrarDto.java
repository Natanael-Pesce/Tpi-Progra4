package com.utn.frvm.prode_api.dtos.registrardto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipoRegistrarDto {
    
    @NotBlank(message = "El equipo debe de tener un nombre")
    private String nombreEquipo;
}
