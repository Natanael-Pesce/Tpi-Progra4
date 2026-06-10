package com.utn.frvm.prode_api.models;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.utility.Resultado;

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
public class Partido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPartido;

    private int idEquipoLocal;
    private int idEquipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private EstadoPartido estadoPartido = EstadoPartido.PROGRAMADO;
    private int idJornada;
    private LocalDateTime horaInicio;
    private Resultado resultado = Resultado.SIN_DEFINIR;
    private boolean estaActivo = true;
}
