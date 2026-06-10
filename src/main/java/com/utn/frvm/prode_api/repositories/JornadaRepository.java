package com.utn.frvm.prode_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.utn.frvm.prode_api.models.Jornada;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Integer> {

    Optional<Jornada> findById(int idJornada);
    
}
