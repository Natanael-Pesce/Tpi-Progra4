package com.utn.frvm.prode_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.utn.frvm.prode_api.models.Partido;

@Repository
public interface PartidoRepository extends JpaRepository <Partido, Integer> {

    Optional<Partido> findById(int idPartido);
    
}
