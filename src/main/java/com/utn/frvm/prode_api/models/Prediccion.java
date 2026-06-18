package com.utn.frvm.prode_api.models;

import java.time.LocalDateTime;

import com.utn.frvm.prode_api.utility.Resultado;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @OneToOne //Revisar
    private Usuario usuarioId;

    @OneToOne //Revisar
    private Partido partidoId;
    
    private LocalDateTime fechaCreacion;
    private int golesLocal;
    private int golesVisitante;
    private Resultado resultadoPronosticado;
    private int puntosObtenidos;
    private boolean estaActivo = true;
}
