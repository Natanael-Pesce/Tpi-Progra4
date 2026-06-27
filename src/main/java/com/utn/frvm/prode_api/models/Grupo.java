package com.utn.frvm.prode_api.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "grupos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idGrupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creador_id", nullable = false)
    private Usuario creador;

    @Column(nullable = false, unique = true)
    private String codigoInvitacion;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<MiembroGrupo> miembros = new ArrayList<>();

    /** Genera un código de invitación único de 8 caracteres */
    public void generarCodigo() {
        this.codigoInvitacion = UUID.randomUUID().toString()
                                    .replace("-", "")
                                    .substring(0, 8)
                                    .toUpperCase();
    }
}
