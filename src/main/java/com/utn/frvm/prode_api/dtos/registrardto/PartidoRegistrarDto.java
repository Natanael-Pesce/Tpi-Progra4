package com.utn.frvm.prode_api.dtos.registrardto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PartidoRegistrarDto {
    
    @NotBlank(message = "Se debe de ingresar el id de un equipo")
    private int idEquipoLocal;
    @NotBlank(message = "Se debe de ingresar el id de un equipo")
    private int idEquipoVisitante;
    @NotBlank(message = "Se debe de ingresar el id de la jornada de juego")
    private int idJornada;
    @NotBlank(message = "Se debe de ingresar la fecha de juego")
    private LocalDateTime fechaIncio;
}
