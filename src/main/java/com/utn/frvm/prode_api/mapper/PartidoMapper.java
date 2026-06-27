package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responsedto.PartidoResponse;
import com.utn.frvm.prode_api.models.Partido;

public class PartidoMapper {

    private PartidoMapper() {
    }

    public static PartidoResponse toResponse(Partido partido) {
        return new PartidoResponse(
                partido.getIdPartido(),
                EquipoMapper.toResponse(partido.getEquipoLocal()),
                EquipoMapper.toResponse(partido.getEquipoVisitante()),
                partido.getHoraInicio(),
                partido.getCierrePrediccion(),
                partido.getGolesLocal(),
                partido.getGolesVisitante(),
                partido.getEstadoPartido(),
                partido.getResultadoFinal(),
                partido.getJornada().getIdJornada(),
                partido.getJornada().getNombre(),
                !partido.estaAbiertaParaPredicciones());
    }
}
