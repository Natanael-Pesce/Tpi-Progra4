package com.utn.frvm.prode_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.utn.frvm.prode_api.models.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    Optional<Equipo> findById(int idEquipo);
    
}
