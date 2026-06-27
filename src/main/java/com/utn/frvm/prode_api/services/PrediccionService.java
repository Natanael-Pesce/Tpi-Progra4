package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.requestdto.PrediccionRequest;
import com.utn.frvm.prode_api.dtos.responsedto.PrediccionResponse;
import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.config.exeptions.ResourceNotFoundException;
import com.utn.frvm.prode_api.mapper.PrediccionMapper;
import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.models.Prediccion;
import com.utn.frvm.prode_api.models.Usuario;
import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.repositories.PartidoRepository;
import com.utn.frvm.prode_api.repositories.PrediccionRepository;
import com.utn.frvm.prode_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrediccionService {

    private final PrediccionRepository prediccionRepo;
    private final PartidoRepository    partidoRepo;
    private final UsuarioRepository    usuarioRepo;

    @Transactional(readOnly = true)
    public List<PrediccionResponse> getMisPredicciones(Long usuarioId) {
        return prediccionRepo.findByUsuario_IdUsuarioOrderByFechaCreacionDesc(usuarioId)
                .stream().map(PrediccionMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PrediccionResponse> getMisPrediccionesPorJornada(Long usuarioId, Long jornadaId) {
        return prediccionRepo.findByUsuarioAndJornada(usuarioId, jornadaId)
                .stream().map(PrediccionMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Optional<PrediccionResponse> getPrediccionPropia(Long usuarioId, Long partidoId) {
        return prediccionRepo.findByUsuario_IdUsuarioAndPartido_IdPartido(usuarioId, partidoId)
                .map(PrediccionMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<PrediccionResponse> getPrediccionesDePartido(Long partidoId) {
        Partido partido = findPartidoById(partidoId);
        if (partido.estaAbiertaParaPredicciones()) {
            throw new BadRequestExeption(
                "Las predicciones de otros usuarios no son visibles hasta 30 minutos antes del inicio");
        }
        return prediccionRepo.findByPartido_IdPartido(partidoId)
                .stream().map(PrediccionMapper::toResponse).toList();
    }

    @Transactional
    public PrediccionResponse crear(Long usuarioId, PrediccionRequest req) {
        Partido partido = findPartidoById(req.partidoId());

        if (!partido.estaAbiertaParaPredicciones()) {
            throw new BadRequestExeption(
                "El plazo de predicción ha cerrado (30 minutos antes del inicio)");
        }
        if (partido.getEstadoPartido() != EstadoPartido.PROGRAMADO) {
            throw new BadRequestExeption("Solo se puede predecir un partido PROGRAMADO");
        }
        if (prediccionRepo.findByUsuario_IdUsuarioAndPartido_IdPartido(usuarioId, req.partidoId()).isPresent()) {
            throw new BadRequestExeption(
                "Ya tienes una predicción para este partido. Utiliza modificar.");
        }

        Usuario usuario = findUsuarioById(usuarioId);

        Prediccion prediccion = Prediccion.builder()
                .usuario(usuario)
                .partido(partido)
                .golesLocal(req.golesLocal())
                .golesVisitante(req.golesVisitante())
                .fechaCreacion(LocalDateTime.now())
                .puntosObtenidos(0)
                .build();

        return PrediccionMapper.toResponse(prediccionRepo.save(prediccion));
    }

    @Transactional
    public PrediccionResponse modificar(Long usuarioId, Long prediccionId, PrediccionRequest req) {
        Prediccion prediccion = findById(prediccionId);

        if (!prediccion.getUsuario().getIdUsuario().equals(usuarioId)) {
            throw new BadRequestExeption("No puedes modificar la predicción de otro usuario");
        }
        if (!prediccion.getPartido().estaAbiertaParaPredicciones()) {
            throw new BadRequestExeption(
                "El plazo de modificación ha cerrado (30 minutos antes del inicio)");
        }

        if (prediccion.getPartido().getEstadoPartido() != EstadoPartido.PROGRAMADO) {
            throw new BadRequestExeption("Solo se puede modificar la prediccion de un partido PROGRAMADO");
        }

        prediccion.setGolesLocal(req.golesLocal());
        prediccion.setGolesVisitante(req.golesVisitante());

        return PrediccionMapper.toResponse(prediccionRepo.save(prediccion));
    }

    @Transactional
    public void calcularPuntosParaPartido(Partido partido) {
        List<Prediccion> predicciones = prediccionRepo.findByPartido_IdPartido(partido.getIdPartido());

        for (Prediccion p : predicciones) {
            int puntosAnteriores = p.getPuntosObtenidos();
            p.calcularPuntos(partido);
            prediccionRepo.save(p);

            Usuario usuario = p.getUsuario();
            usuario.setPuntos(usuario.getPuntos() - puntosAnteriores + p.getPuntosObtenidos());
            usuarioRepo.save(usuario);
        }
    }

    // Helpers

    private Prediccion findById(Long id) {
        return prediccionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prediccion", id));
    }

    private Partido findPartidoById(Long id) {
        return partidoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partido", id));
    }

    private Usuario findUsuarioById(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    }
}
