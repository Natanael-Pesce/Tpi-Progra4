package com.utn.frvm.prode_api.repostories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.utn.frvm.prode_api.models.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer>{

    Optional<Grupo> findById(int idGrupo);
    
}
