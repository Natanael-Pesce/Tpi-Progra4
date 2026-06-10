package com.utn.frvm.prode_api.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiembroGrupo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMiembro;

    private int idUsuario;
    private int idGrupo;
    private LocalDateTime fechaIngreso;
}
