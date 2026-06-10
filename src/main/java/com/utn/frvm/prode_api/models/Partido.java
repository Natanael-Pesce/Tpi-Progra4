package com.utn.frvm.prode_api.models;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.utility.Resultado;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPartido;
    @ManyToOne
    @JoinColumn(name = "id_equipo_local")
    private Equipo equipoLocal;
    @ManyToOne
    @JoinColumn(name = "id_equipo_visitante")
    private Equipo equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    @Enumerated(EnumType.STRING)
    private EstadoPartido estadoPartido = EstadoPartido.PROGRAMADO;
    @ManyToOne
    @JoinColumn(name = "id_jornada")
    private Jornada jornada;
    private LocalDateTime horaInicio;
    @Enumerated(EnumType.STRING)
    private Resultado resultado = Resultado.SIN_DEFINIR;
    private boolean estaActivo = true;
}
