package com.utn.frvm.prode_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utn.frvm.prode_api.models.Prediccion;

@Repository
public interface PrediccionRepository extends JpaRepository<Prediccion, Integer> {

    Optional<Prediccion> findById(int idPrediccion);

}
