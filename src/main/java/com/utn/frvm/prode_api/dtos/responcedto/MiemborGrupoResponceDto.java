package com.utn.frvm.prode_api.dtos.responcedto;

import com.utn.frvm.prode_api.models.Grupo;
import com.utn.frvm.prode_api.models.Usuario;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MiemborGrupoResponceDto {

    private int idMiembroGrupo;
    private Usuario usuario;
    private Grupo grupo;   
    private LocalDateTime fechaIngreso;
}
