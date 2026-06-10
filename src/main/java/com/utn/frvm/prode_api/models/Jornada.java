package com.utn.frvm.prode_api.models;

import java.time.LocalDateTime;
import java.util.List;

import com.utn.frvm.prode_api.utility.EstadoJornada;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Enumerated;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idJornada;

    private String nombreJornada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    @OneToMany(mappedBy = "jornada")
    private List<Partido> partidos;
    @Enumerated(EnumType.STRING)
    private EstadoJornada estadoJornada = EstadoJornada.PROGRAMADA;
    private boolean estaActivo = true;
}
