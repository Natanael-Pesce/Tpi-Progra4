package com.utn.frvm.prode_api.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGrupo;

    private String nombreGrupo;

    @ManyToOne
    @JoinColumn(name = "id_creador_grupo")
    private Usuario creadorGrupo;

    @OneToMany(mappedBy = "grupo")
    private List<MiembroGrupo> miembros;

    private String codigoInvitacion;
    private boolean estaActivo = true;
}
