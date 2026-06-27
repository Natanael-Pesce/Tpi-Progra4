package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.requestdto.CrearGrupoRequest;
import com.utn.frvm.prode_api.dtos.requestdto.UnirseGrupoRequest;
import com.utn.frvm.prode_api.dtos.responsedto.GrupoResponse;
import com.utn.frvm.prode_api.dtos.responsedto.RankingItemResponse;
import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.config.exeptions.ResourceNotFoundException;
import com.utn.frvm.prode_api.mapper.GrupoMapper;
import com.utn.frvm.prode_api.models.Grupo;
import com.utn.frvm.prode_api.models.MiembroGrupo;
import com.utn.frvm.prode_api.models.Usuario;
import com.utn.frvm.prode_api.repositories.GrupoRepository;
import com.utn.frvm.prode_api.repositories.MiembroGrupoRepository;
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
public class GrupoService {

    private final GrupoRepository        grupoRepo;
    private final MiembroGrupoRepository miembroRepo;
    private final UsuarioRepository      usuarioRepo;
    private final PrediccionRepository   prediccionRepo;

    @Transactional(readOnly = true)
    public List<GrupoResponse> getMisGrupos(Long idUsuario) {
        return miembroRepo.findByUsuario_IdUsuario(idUsuario).stream()
                .map(m -> GrupoMapper.toResponse(m.getGrupo()))
                .toList();
    }

    @Transactional(readOnly = true)
    public GrupoResponse getById(Long idGrupo, Long idUsuario) {
        Grupo grupo = findById(idGrupo);
        validarMiembro(idGrupo, idUsuario);
        return GrupoMapper.toResponse(grupo);
    }

    @Transactional
    public GrupoResponse crear(Long idUsuario, CrearGrupoRequest req) {
        Usuario creador = findUsuario(idUsuario);

        Grupo grupo = Grupo.builder()
                .nombre(req.nombre())
                .descripcion(req.descripcion())
                .creador(creador)
                .miembros(new ArrayList<>())
                .build();

        do {
            grupo.generarCodigo();
        } while (grupoRepo.existsByCodigoInvitacion(grupo.getCodigoInvitacion()));

        grupoRepo.save(grupo);

        MiembroGrupo miembro = MiembroGrupo.builder()
                .grupo(grupo).usuario(creador)
                .fechaIngreso(LocalDateTime.now()).build();
        miembroRepo.save(miembro);

        return GrupoMapper.toResponse(findById(grupo.getIdGrupo()));
    }

    @Transactional
    public GrupoResponse unirse(Long idUsuario, UnirseGrupoRequest req) {
        Grupo grupo = grupoRepo.findByCodigoInvitacion(req.codigoInvitacion())
                .orElseThrow(() -> new BadRequestExeption("Código de invitación inválido o inexistente"));

        if (miembroRepo.existsByGrupo_IdGrupoAndUsuario_IdUsuario(grupo.getIdGrupo(), idUsuario)) {
            throw new BadRequestExeption("Ya eres miembro de este grupo");
        }

        Usuario usuario = findUsuario(idUsuario);
        miembroRepo.save(MiembroGrupo.builder()
                .grupo(grupo).usuario(usuario)
                .fechaIngreso(LocalDateTime.now()).build());

        return GrupoMapper.toResponse(findById(grupo.getIdGrupo()));
    }

    @Transactional
    public void abandonar(Long idGrupo, Long idUsuario) {
        Grupo grupo = findById(idGrupo);

        MiembroGrupo miembro = miembroRepo.findByGrupo_IdGrupoAndUsuario_IdUsuario(idGrupo, idUsuario)
                .orElseThrow(() -> new BadRequestExeption("No eres miembro de este grupo"));

        if (grupo.getCreador().getIdUsuario().equals(idUsuario)) {
            throw new BadRequestExeption(
                "Eres el administrador del grupo. Transfiere la administración antes de abandonarlo.");
        }
        miembroRepo.delete(miembro);
    }

    @Transactional(readOnly = true)
    public List<RankingItemResponse> getRankingGrupo(Long grupoId, Long idUsuario) {
        validarMiembro(grupoId, idUsuario);
        List<MiembroRanking> miembros = miembroRepo.findRankingByGrupo(grupoId).stream()
                .map(miembro -> new MiembroRanking(
                        miembro,
                        prediccionRepo.contarExactos(miembro.getUsuario().getIdUsuario()),
                        prediccionRepo.fechaPrimeraPrediccion(miembro.getUsuario().getIdUsuario())))
                .sorted(Comparator
                        .comparing((MiembroRanking item) -> item.miembro().getUsuario().getPuntos(),
                                Comparator.reverseOrder())
                        .thenComparing(MiembroRanking::exactos, Comparator.reverseOrder())
                        .thenComparing(MiembroRanking::primeraPrediccion,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        List<RankingItemResponse> ranking = new ArrayList<>();
        int posicion = 1;

        for (MiembroRanking item : miembros) {
            MiembroGrupo miembro = item.miembro();
            Usuario usuario = miembro.getUsuario();
            ranking.add(new RankingItemResponse(
                    posicion++,
                    usuario.getIdUsuario(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getPuntos(),
                    prediccionRepo.contarAciertos(usuario.getIdUsuario()),
                    usuario.getIdUsuario().equals(idUsuario)));
        }

        return ranking;
    }

    private void validarMiembro(Long grupoId, Long idUsuario) {
        if (!miembroRepo.existsByGrupo_IdGrupoAndUsuario_IdUsuario(grupoId, idUsuario)) {
            throw new BadRequestExeption("No tienes acceso a este grupo");
        }
    }

    private Grupo findById(Long id) {
        return grupoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo", id));
    }

    private Usuario findUsuario(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    }

    private record MiembroRanking(
            MiembroGrupo miembro,
            Long exactos,
            LocalDateTime primeraPrediccion
    ) {}
}
