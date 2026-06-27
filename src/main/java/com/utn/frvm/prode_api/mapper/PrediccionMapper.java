package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responsedto.PrediccionResponse;
import com.utn.frvm.prode_api.models.Prediccion;

public class PrediccionMapper {

    private PrediccionMapper() {
    }

    public static PrediccionResponse toResponse(Prediccion prediccion) {
        return new PrediccionResponse(
                prediccion.getIdPrediccion(),
                prediccion.getUsuario().getIdUsuario(),
                prediccion.getUsuario().getNombre() + " " + prediccion.getUsuario().getApellido(),
                PartidoMapper.toResponse(prediccion.getPartido()),
                prediccion.getFechaCreacion(),
                prediccion.getGolesLocal(),
                prediccion.getGolesVisitante(),
                prediccion.getResultadoPronosticado(),
                prediccion.getPuntosObtenidos());
    }
}
