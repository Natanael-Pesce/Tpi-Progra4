package com.utn.frvm.prode_api.dtos.registrardto;


import com.utn.frvm.prode_api.models.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GrupoRegistrarDto {
    
    @NotBlank(message = "El grupo debe de tener un nombre")
    private String nombreGrupo;

    private Usuario usuario;
}
