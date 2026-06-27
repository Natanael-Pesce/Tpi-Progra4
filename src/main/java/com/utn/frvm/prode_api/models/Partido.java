package com.utn.frvm.prode_api.models;

import java.time.LocalDateTime;
import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.utility.Resultado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "partidos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPartido")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipo_local_id", nullable = false)
    private Equipo equipoLocal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipo_visitante_id", nullable = false)
    private Equipo equipoVisitante;

    @Column(nullable = false)
    private LocalDateTime horaInicio;

    @Builder.Default
    @Column(nullable = false)
    private Integer golesLocal = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer golesVisitante = 0;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private EstadoPartido estadoPartido = EstadoPartido.PROGRAMADO;

    @Enumerated(EnumType.STRING)
    private Resultado resultadoFinal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jornada_id", nullable = false)
    private Jornada jornada;

    /** Regla de negocio: cierre de predicciones 30 min antes del inicio */
    public LocalDateTime getCierrePrediccion() {
        return horaInicio.minusMinutes(30);
    }

    /** Verifica si todavía se pueden crear/modificar predicciones */
    public boolean estaAbiertaParaPredicciones() {
        return LocalDateTime.now().isBefore(getCierrePrediccion());
    }

    /** Finaliza el partido y determina el resultado oficial */
    public void finalizarPartido(int golesLocal, int golesVisitante) {
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.estadoPartido = EstadoPartido.FINALIZADO;
        this.resultadoFinal = Resultado.calcularDesde(golesLocal, golesVisitante);
    }

    public void iniciarPartido() {
        this.estadoPartido = EstadoPartido.EN_JUEGO;
    }
}
