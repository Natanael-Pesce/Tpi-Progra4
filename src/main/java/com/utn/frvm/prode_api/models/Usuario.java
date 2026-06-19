package com.utn.frvm.prode_api.models;

import com.utn.frvm.prode_api.utility.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    private String nombre;
    private String apellido;

    @Email
    private String correo;

    private String contraseña;

    @OneToMany //Revisar
    private List<Prediccion> predicciones;
    
    private int puntos;
    private Rol rol = Rol.USUARIO;
}
