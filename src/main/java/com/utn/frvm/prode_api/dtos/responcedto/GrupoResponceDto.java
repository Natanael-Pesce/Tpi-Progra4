package com.utn.frvm.prode_api.dtos.responcedto;

import java.util.List;

import com.utn.frvm.prode_api.models.MiembroGrupo;
import com.utn.frvm.prode_api.models.Usuario;

import lombok.Data;

@Data
public class GrupoResponceDto {
    
    private int idGrupo;
    private String nombreGrupo;
    private Usuario creadorGrupo;
    private List<MiembroGrupo> miembros;
}
