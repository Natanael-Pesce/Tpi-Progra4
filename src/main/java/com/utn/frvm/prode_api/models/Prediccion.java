package com.utn.frvm.prode_api.models;

import java.time.LocalDateTime;

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
public class Prediccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrediccion;

    private int usuarioId;
    private int partidoId;
    private LocalDateTime fechaCreacion;
    private int golesLocal;
    private int golesVisitante;
    private Resultado resultadoPronosticado = Resultado.SIN_DEFINIR;
    private int puntosObtenidos;
    private boolean estaAtivo = true;
}
