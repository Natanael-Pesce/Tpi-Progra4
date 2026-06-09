package com.utn.frvm.prode_api.repostories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utn.frvm.prode_api.models.MiembroGrupo;

@Repository
public interface MiembroGrupoRepository extends JpaRepository <MiembroGrupo,Integer> {

    Optional<MiembroGrupo> findById(int idMiembroGrupo);
    
}