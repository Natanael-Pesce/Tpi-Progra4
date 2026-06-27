package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.responsedto.RankingItemResponse;
import com.utn.frvm.prode_api.config.exeptions.ResourceNotFoundException;
import com.utn.frvm.prode_api.models.Usuario;
import com.utn.frvm.prode_api.utility.Rol;
import com.utn.frvm.prode_api.repositories.JornadaRepository;
import com.utn.frvm.prode_api.repositories.PrediccionRepository;
import com.utn.frvm.prode_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final UsuarioRepository    usuarioRepo;
    private final PrediccionRepository prediccionRepo;
    private final JornadaRepository    jornadaRepo;

    @Transactional(readOnly = true)
    public List<RankingItemResponse> getRankingGlobal(Long usuarioActualId) {
        List<UsuarioRanking> usuarios = usuarioRepo.findByRolOrderByPuntosDesc(Rol.USUARIO).stream()
                .map(usuario -> new UsuarioRanking(
                        usuario,
                        prediccionRepo.contarExactos(usuario.getIdUsuario()),
                        prediccionRepo.fechaPrimeraPrediccion(usuario.getIdUsuario())))
                .sorted(Comparator
                        .comparing((UsuarioRanking item) -> item.usuario().getPuntos(), Comparator.reverseOrder())
                        .thenComparing(UsuarioRanking::exactos, Comparator.reverseOrder())
                        .thenComparing(UsuarioRanking::primeraPrediccion,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        List<RankingItemResponse> ranking = new ArrayList<>();
        int posicion = 1;

        for (UsuarioRanking item : usuarios) {
            Usuario usuario = item.usuario();
            Long aciertos = prediccionRepo.contarAciertos(usuario.getIdUsuario());
            ranking.add(new RankingItemResponse(
                    posicion++,
                    usuario.getIdUsuario(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getPuntos(),
                    aciertos,
                    usuario.getIdUsuario().equals(usuarioActualId)));
        }

        return ranking;
    }

    @Transactional(readOnly = true)
    public List<RankingItemResponse> getRankingPorJornada(Long jornadaId, Long usuarioActualId) {
        if (!jornadaRepo.existsById(jornadaId)) {
            throw new ResourceNotFoundException("Jornada", jornadaId);
        }

        List<Object[]> resultadosPorUsuario = prediccionRepo.rankingPorJornada(jornadaId);
        List<JornadaRanking> usuarios = resultadosPorUsuario.stream()
                .map(fila -> {
                    Usuario usuario = (Usuario) fila[0];
                    int puntosJornada = ((Number) fila[1]).intValue();
                    return new JornadaRanking(
                            usuario,
                            puntosJornada,
                            prediccionRepo.contarExactosPorJornada(usuario.getIdUsuario(), jornadaId),
                            prediccionRepo.fechaPrimeraPrediccionPorJornada(usuario.getIdUsuario(), jornadaId));
                })
                .sorted(Comparator
                        .comparingInt(JornadaRanking::puntos).reversed()
                        .thenComparing(JornadaRanking::exactos, Comparator.reverseOrder())
                        .thenComparing(JornadaRanking::primeraPrediccion,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        List<RankingItemResponse> ranking = new ArrayList<>();
        int posicion = 1;

        for (JornadaRanking item : usuarios) {
            Usuario usuario = item.usuario();
            Long aciertos = prediccionRepo.contarAciertos(usuario.getIdUsuario());

            ranking.add(new RankingItemResponse(
                    posicion++,
                    usuario.getIdUsuario(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    item.puntos(),
                    aciertos,
                    usuario.getIdUsuario().equals(usuarioActualId)));
        }
        return ranking;
    }

    private record UsuarioRanking(
            Usuario usuario,
            Long exactos,
            LocalDateTime primeraPrediccion
    ) {}

    private record JornadaRanking(
            Usuario usuario,
            int puntos,
            Long exactos,
            LocalDateTime primeraPrediccion
    ) {}
}
