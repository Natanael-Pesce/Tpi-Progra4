package com.utn.frvm.prode_api.dtos.updateDto;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.utility.Resultado;

import lombok.Data;

@Data
public class PrediccionUpdateDto {
    private int usuarioId;
    private int partidoId;
    private LocalDateTime fechaCreacion;
    private int golesLocal;
    private int golesVisitante;
    private Resultado resultadoPronosticado;
    private int puntosObtenidos;
}
