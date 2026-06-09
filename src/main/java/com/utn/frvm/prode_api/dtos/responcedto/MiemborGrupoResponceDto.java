package com.utn.frvm.prode_api.dtos.responcedto;

import com.utn.frvm.prode_api.models.Grupo;
import com.utn.frvm.prode_api.models.Usuario;

import lombok.Data;

@Data
public class MiemborGrupoResponceDto {

    private Usuario usuario;
    private Grupo grupo;   
}
