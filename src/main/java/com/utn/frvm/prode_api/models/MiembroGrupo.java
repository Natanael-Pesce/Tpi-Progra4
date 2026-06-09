package com.utn.frvm.prode_api.models;

import java.time.LocalDate;

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

    private Usuario usuario;
    private Grupo grupo;
    private LocalDate fechaIngreso;
}
