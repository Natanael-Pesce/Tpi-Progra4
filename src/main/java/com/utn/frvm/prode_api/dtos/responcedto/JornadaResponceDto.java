package com.utn.frvm.prode_api.dtos.responcedto;

import java.time.LocalDateTime;
import java.util.List;
import com.utn.frvm.prode_api.utility.EstadoJornada;
import lombok.Data;

@Data
public class JornadaResponceDto {

    private int idJornada;
    private String nombreJornada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Integer> idPartidos;
    private EstadoJornada estadoJornada;
    private boolean estaActivo;
}
