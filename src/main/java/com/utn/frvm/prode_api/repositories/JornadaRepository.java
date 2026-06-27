package com.utn.frvm.prode_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import com.utn.frvm.prode_api.models.Jornada;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {

    List<Jornada> findAllByOrderByFechaInicioAsc();

    Optional<Jornada> findByNombreIgnoreCase(String nombre);
    
}
