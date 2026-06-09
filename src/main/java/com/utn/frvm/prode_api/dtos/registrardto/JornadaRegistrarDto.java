package com.utn.frvm.prode_api.dtos.registrardto;

import java.sql.Date;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JornadaRegistrarDto {
    
    @NotBlank(message = "La jornada debe de tener nombre")
    private String nombreJornada;

    @NotBlank(message = "La jornada debe de tener una fecha de inicio")
    private Date fechaInicio;

    @NotBlank(message = "La jornada debe de tener una fecha de terminacion")
    private Date fechaFin;
}
