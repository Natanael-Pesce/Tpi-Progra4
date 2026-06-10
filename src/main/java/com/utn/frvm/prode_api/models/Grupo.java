package com.utn.frvm.prode_api.models;

import java.util.List;

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
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGrupo;

    private String nombreGrupo;
    private int idCreadorGrupo;
    private List<Integer> idMiembros;
    private String codigoInvitacion;
    private boolean estaActivo = true;
}
