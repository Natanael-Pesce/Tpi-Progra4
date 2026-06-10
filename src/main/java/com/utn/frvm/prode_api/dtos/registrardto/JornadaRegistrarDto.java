package com.utn.frvm.prode_api.dtos.registrardto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JornadaRegistrarDto {
    
    @NotBlank(message = "La jornada debe de tener nombre")
    private String nombreJornada;

    @NotBlank(message = "La jornada debe de tener una fecha de inicio")
    private LocalDateTime fechaInicio;

    @NotBlank(message = "La jornada debe de tener una fecha de terminacion")
    private LocalDateTime fechaFin;
}
