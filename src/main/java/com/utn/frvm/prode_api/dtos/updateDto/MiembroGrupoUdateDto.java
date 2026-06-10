package com.utn.frvm.prode_api.dtos.updatedto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MiembroGrupoUdateDto {
    
    private int idUsuario;
    private int idGrupo;
    private LocalDate fechaIngreso;
}
