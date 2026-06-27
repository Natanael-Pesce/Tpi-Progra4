package com.utn.frvm.prode_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.utility.EstadoPartido;

@Repository
public interface PartidoRepository extends JpaRepository <Partido, Long> {

    List<Partido> findAllByOrderByHoraInicioAsc();

    List<Partido> findByJornada_IdJornadaOrderByHoraInicioAsc(Long idJornada);

    List<Partido> findByEstadoPartidoOrderByHoraInicioAsc(EstadoPartido estado);

    List<Partido> findByJornada_IdJornadaAndEstadoPartidoOrderByHoraInicioAsc(Long idJornada, EstadoPartido estado);

    boolean existsByJornada_IdJornada(Long idJornada);

    boolean existsByEquipoLocal_IdEquipoOrEquipoVisitante_IdEquipo(Long idEquipoLocal, Long idEquipoVisitante);
    
}
