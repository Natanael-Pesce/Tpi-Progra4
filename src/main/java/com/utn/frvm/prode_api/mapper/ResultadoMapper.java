package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responsedto.ResultadoResponse;
import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.models.Prediccion;

public class ResultadoMapper {

    private ResultadoMapper() {
    }

    public static ResultadoResponse toResponse(Partido partido, Prediccion prediccion) {
        return new ResultadoResponse(
                partido.getIdPartido(),
                EquipoMapper.toResponse(partido.getEquipoLocal()),
                EquipoMapper.toResponse(partido.getEquipoVisitante()),
                partido.getHoraInicio(),
                partido.getGolesLocal(),
                partido.getGolesVisitante(),
                partido.getResultadoFinal(),
                partido.getJornada().getIdJornada(),
                partido.getJornada().getNombre(),
                prediccion != null ? prediccion.getGolesLocal() : null,
                prediccion != null ? prediccion.getGolesVisitante() : null,
                prediccion != null ? prediccion.getResultadoPronosticado() : null,
                prediccion != null ? prediccion.getPuntosObtenidos() : null);
    }
}