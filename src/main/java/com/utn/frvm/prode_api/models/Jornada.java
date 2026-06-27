package com.utn.frvm.prode_api.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utn.frvm.prode_api.utility.EstadoJornada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "jornadas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idJornada")
public class Jornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJornada;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private EstadoJornada estadoJornada = EstadoJornada.PROGRAMADA;

    @OneToMany(mappedBy = "jornada", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Partido> partidos = new ArrayList<>();

    public void cerrarFase() {
        this.estadoJornada = EstadoJornada.FINALIZADA;
    }

    public void iniciarFase() {
        this.estadoJornada = EstadoJornada.EN_JUEGO;
    }
}
