package com.utn.frvm.prode_api.repositories;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utn.frvm.prode_api.models.Prediccion;

@Repository
public interface PrediccionRepository extends JpaRepository<Prediccion, Long> {

    Optional<Prediccion> findByUsuario_IdUsuarioAndPartido_IdPartido(Long idUsuario, Long idPartido);

    List<Prediccion> findByUsuario_IdUsuarioOrderByFechaCreacionDesc(Long idUsuario);

    List<Prediccion> findByUsuario_IdUsuarioOrderByFechaCreacionAsc(Long idUsuario);

    List<Prediccion> findByPartido_IdPartido(Long idPartido);

    boolean existsByPartido_IdPartido(Long idPartido);

    @Query("SELECT p FROM Prediccion p WHERE p.usuario.id = :idUsuario " +
           "AND p.partido.jornada.id = :idJornada ORDER BY p.fechaCreacion DESC")
    List<Prediccion> findByUsuarioAndJornada(@Param("idUsuario") Long idUsuario,
                                              @Param("idJornada") Long idJornada);

    @Query("SELECT p.partido.jornada.id, p.partido.jornada.nombre, SUM(p.puntosObtenidos) " +
           "FROM Prediccion p WHERE p.usuario.id = :idUsuario " +
           "GROUP BY p.partido.jornada.id, p.partido.jornada.nombre " +
           "ORDER BY MIN(p.partido.jornada.fechaInicio) ASC")
    List<Object[]> evolucionPorJornada(@Param("idUsuario") Long idUsuario);

    @Query("SELECT p.usuario, SUM(p.puntosObtenidos) FROM Prediccion p " +
           "WHERE p.partido.jornada.id = :idJornada " +
           "GROUP BY p.usuario ORDER BY SUM(p.puntosObtenidos) DESC")
    List<Object[]> rankingPorJornada(@Param("idJornada") Long idJornada);

    @Query("SELECT COUNT(p) FROM Prediccion p " +
           "WHERE p.usuario.id = :idUsuario AND p.puntosObtenidos > 0")
    Long contarAciertos(@Param("idUsuario") Long idUsuario);

    @Query("SELECT COUNT(p) FROM Prediccion p WHERE p.usuario.id = :idUsuario")
    Long contarTotalPredicciones(@Param("idUsuario") Long idUsuario);

    @Query("SELECT COUNT(p) FROM Prediccion p " +
           "WHERE p.usuario.id = :idUsuario AND p.puntosObtenidos = 3")
    Long contarExactos(@Param("idUsuario") Long idUsuario);

    @Query("SELECT MIN(p.fechaCreacion) FROM Prediccion p " +
           "WHERE p.usuario.id = :idUsuario")
    LocalDateTime fechaPrimeraPrediccion(@Param("idUsuario") Long idUsuario);

    @Query("SELECT COUNT(p) FROM Prediccion p " +
           "WHERE p.usuario.id = :idUsuario AND p.partido.jornada.id = :idJornada " +
           "AND p.puntosObtenidos = 3")
    Long contarExactosPorJornada(@Param("idUsuario") Long idUsuario,
                                  @Param("idJornada") Long idJornada);

    @Query("SELECT MIN(p.fechaCreacion) FROM Prediccion p " +
           "WHERE p.usuario.id = :idUsuario AND p.partido.jornada.id = :idJornada")
    LocalDateTime fechaPrimeraPrediccionPorJornada(@Param("idUsuario") Long idUsuario,
                                                   @Param("idJornada") Long jornadaId);

}
