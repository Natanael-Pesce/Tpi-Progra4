package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responsedto.JornadaResponse;
import com.utn.frvm.prode_api.models.Jornada;

public class JornadaMapper {

    private JornadaMapper() {
    }

    public static JornadaResponse toResponse(Jornada jornada) {
        return new JornadaResponse(
                jornada.getIdJornada(),
                jornada.getNombre(),
                jornada.getFechaInicio(),
                jornada.getFechaFin(),
                jornada.getEstadoJornada(),
                jornada.getPartidos().size());
    }
}

