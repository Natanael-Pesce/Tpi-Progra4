package com.utn.frvm.prode_api.dtos.registrardto;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.models.Usuario;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PrediccionRegistrarDto {
    
    private Usuario usuarioId;
    private Partido partidoId;
    private LocalDateTime fechaCreacion;

    @PositiveOrZero(message = "El equipo local debe de tener goles en positivo")
    private int golesLocal;

    @PositiveOrZero(message = "El El equipo visitante debe de tener goles en positivo")
    private int golesVisitante;

}
