package com.utn.frvm.prode_api.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
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
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;
    private LocalDateTime fechaIngreso;
}
