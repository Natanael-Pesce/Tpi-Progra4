package com.utn.frvm.prode_api.dtos.responcedto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MiemborGrupoResponceDto {

    private int idMiembroGrupo;
    private int idUsuario;
    private int idGrupo;   
    private LocalDateTime fechaIngreso;
}
