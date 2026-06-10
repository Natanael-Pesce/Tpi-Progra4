package com.utn.frvm.prode_api.dtos.responcedto;

import java.util.List;
import lombok.Data;

@Data
public class GrupoResponceDto {
    
    private int idGrupo;
    private String nombreGrupo;
    private int IdcreadorGrupo;
    private List<Integer> idsMiembros;
}
