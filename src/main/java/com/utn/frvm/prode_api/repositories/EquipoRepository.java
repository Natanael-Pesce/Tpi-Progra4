package com.utn.frvm.prode_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import com.utn.frvm.prode_api.models.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByEstaActivoTrue();

    List<Equipo> findByNombreContainingIgnoreCaseAndEstaActivoTrue(String nombre);

    Optional<Equipo> findByNombreIgnoreCase(String nombre);
    
}
