package com.utn.frvm.prode_api.dtos.responsedto;

import java.util.List;

import com.utn.frvm.prode_api.models.MiembroGrupo;
import com.utn.frvm.prode_api.models.Usuario;

import lombok.Data;

@Data
public class GrupoResponseDto {
    
    private int idGrupo;
    private String nombreGrupo;
    private Usuario creadorGrupo;
    private List<MiembroGrupo> miembros;
}
