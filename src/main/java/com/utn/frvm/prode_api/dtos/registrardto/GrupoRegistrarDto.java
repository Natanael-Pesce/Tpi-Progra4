package com.utn.frvm.prode_api.dtos.registrardto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GrupoRegistrarDto {
    
    @NotBlank(message = "El grupo debe de tener un nombre")
    private String nombreGrupo;

    private int idUsuario;
}
