package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responsedto.EquipoResponse;
import com.utn.frvm.prode_api.models.Equipo;

public class EquipoMapper {

    private EquipoMapper() {
    }

    public static EquipoResponse toResponse(Equipo equipo) {
        return new EquipoResponse(
                equipo.getIdEquipo(),
                equipo.getNombre(),
                equipo.getEstaActivo());
    }
}

