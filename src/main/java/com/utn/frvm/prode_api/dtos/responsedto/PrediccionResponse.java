package com.utn.frvm.prode_api.dtos.responsedto;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.models.Usuario;
import com.utn.frvm.prode_api.utility.Resultado;
import lombok.Data;

public record PrediccionResponse(
    Long id,
    Long usuarioId,
    String nombreUsuario,
    PartidoResponse partido,
    LocalDateTime fechaCreacion,
    Integer golesLocal,
    Integer golesVisitante,
    Resultado resultadoPronosticado,
    Integer puntosObtenidos
) {}
