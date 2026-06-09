package com.utn.frvm.prode_api.dtos.registrardto;

import lombok.Data;

@Data
public class RegistrarPrediccionDto {
    
    private int usuarioId;
    private int partidoId;
    private int fechaCreacion;
    private int golesLocal;
    private int golesVisitante;
}
