package com.utn.frvm.prode_api.dtos.responsedto;

import java.time.LocalDateTime;
import com.utn.frvm.prode_api.models.Grupo;
import com.utn.frvm.prode_api.models.Usuario;
import lombok.Data;

@Data
public class MiembroGrupoResponseDto {

    private Long idMiembroGrupo;
    private Usuario Usuario;
    private Grupo Grupo;   
    private LocalDateTime fechaIngreso;
}
