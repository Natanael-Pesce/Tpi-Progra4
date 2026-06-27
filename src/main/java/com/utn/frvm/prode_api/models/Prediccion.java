package com.utn.frvm.prode_api.models;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.time.LocalDateTime;
import lombok.Builder;
import com.utn.frvm.prode_api.utility.Resultado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "predicciones",uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "partido_id"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPrediccion")
public class Prediccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrediccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partido_id", nullable = false)
    private Partido partido;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private Integer golesLocal;

    @Column(nullable = false)
    private Integer golesVisitante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Resultado resultadoPronosticado;

    @Builder.Default
    @Column(nullable = false)
    private Integer puntosObtenidos = 0;

    /** Deriva resultadoPronosticado de los goles antes de persistir */
    @PrePersist
    @PreUpdate
    public void derivarResultado() {
        if (golesLocal != null && golesVisitante != null) {
            this.resultadoPronosticado = Resultado.calcularDesde(golesLocal, golesVisitante);
        }
    }

    /**
     * Reglas de puntuación:
     *  - Marcador exacto (ambos goles) → 3 puntos
     *  - Solo resultado correcto (L/V/E) → 1 punto
     *  - Error                          → 0 puntos
     */
    public void calcularPuntos(Partido partido) {
        if (partido.getResultadoFinal() == null) {
            this.puntosObtenidos = 0;
            return;
        }
        boolean marcadorExacto =
            this.golesLocal.equals(partido.getGolesLocal()) &&
            this.golesVisitante.equals(partido.getGolesVisitante());

        if (marcadorExacto) {
            this.puntosObtenidos = 3;
        } else if (this.resultadoPronosticado == partido.getResultadoFinal()) {
            this.puntosObtenidos = 1;
        } else {
            this.puntosObtenidos = 0;
        }
    }
}
