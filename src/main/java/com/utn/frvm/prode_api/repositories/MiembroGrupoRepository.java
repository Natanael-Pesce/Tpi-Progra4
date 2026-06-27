package com.utn.frvm.prode_api.repositories;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utn.frvm.prode_api.models.MiembroGrupo;

@Repository
public interface MiembroGrupoRepository extends JpaRepository <MiembroGrupo,Long> {

    List<MiembroGrupo> findByUsuario_IdUsuario(Long usuarioId);

    Optional<MiembroGrupo> findByGrupo_IdGrupoAndUsuario_IdUsuario(Long idGrupo, Long idUsuario);

    boolean existsByGrupo_IdGrupoAndUsuario_IdUsuario(Long idGrupo, Long idUsuario);

    @Query("SELECT mg FROM MiembroGrupo mg JOIN mg.usuario u " +
           "WHERE mg.grupo.id = :idGrupo ORDER BY u.puntos DESC")
    List<MiembroGrupo> findRankingByGrupo(@Param("idGrupo") Long idGrupo);
    
}
