package com.utn.frvm.prode_api.dtos.responsedto;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.models.Usuario;
import com.utn.frvm.prode_api.utility.Resultado;
import lombok.Data;

@Data
public class PrediccionResponseDto {
    
    private int idPrediccion;
    private Usuario usuarioId;
    private Partido partidoId;
    private LocalDateTime fechaCreacion;
    private int golesLocal;
    private int golesVisitante;
    private Resultado resultadoPronosticado = Resultado.SIN_DEFINIR;
    private int puntosObtenidos;
}
