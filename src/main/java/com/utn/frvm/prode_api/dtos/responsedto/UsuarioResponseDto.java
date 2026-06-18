package com.utn.frvm.prode_api.dtos.responsedto;

import com.utn.frvm.prode_api.utility.Rol;
import com.utn.frvm.prode_api.models.Prediccion;
import jakarta.validation.constraints.Email;
import java.util.List;
import lombok.Data;

@Data
public class UsuarioResponseDto {
    
    private int idUsuario;
    private String nombre;
    private String apellido;

    @Email
    private String correo;
    private List<Prediccion> predicciones;
    private int puntos;
    private Rol rol;
}
