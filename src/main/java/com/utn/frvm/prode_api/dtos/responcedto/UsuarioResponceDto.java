package com.utn.frvm.prode_api.dtos.responcedto;

import com.utn.frvm.prode_api.utility.Rol;
import jakarta.validation.constraints.Email;
import java.util.List;
import lombok.Data;

@Data
public class UsuarioResponceDto {
    
    private int idUsuario;
    private String nombre;
    private String apellido;

    @Email
    private String correo;
    private List<Integer> idPredicciones;
    private int puntos;
    private Rol rol;
}
