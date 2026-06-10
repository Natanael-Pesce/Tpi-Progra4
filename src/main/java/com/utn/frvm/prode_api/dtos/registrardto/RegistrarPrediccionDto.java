package com.utn.frvm.prode_api.dtos.registrardto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrarPrediccionDto {
    
    private int usuarioId;
    private int partidoId;
    private int fechaCreacion;
    @NotBlank(message = "Se debe de ingresar los goles del local")
    private int golesLocal;
    @NotBlank(message = "Se debe de ingresar los goles del visitante")
    private int golesVisitante;
}
