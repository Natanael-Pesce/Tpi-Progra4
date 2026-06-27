package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.requestdto.FinalizarPartidoRequest;
import com.utn.frvm.prode_api.dtos.requestdto.PartidoRequest;
import com.utn.frvm.prode_api.dtos.responsedto.PartidoResponse;
import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.config.exeptions.ResourceNotFoundException;
import com.utn.frvm.prode_api.mapper.PartidoMapper;
import com.utn.frvm.prode_api.models.Equipo;
import com.utn.frvm.prode_api.models.Jornada;
import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.repositories.PartidoRepository;
import com.utn.frvm.prode_api.repositories.PrediccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartidoService {

    private final PartidoRepository partidoRepo;
    private final EquipoService equipoService;
    private final JornadaService jornadaService;
    private final PrediccionService prediccionService;
    private final PrediccionRepository prediccionRepo;

    public List<PartidoResponse> listarTodos() {
        return partidoRepo.findAllByOrderByHoraInicioAsc().stream()
                .map(PartidoMapper::toResponse).toList();
    }

    public List<PartidoResponse> listarPorJornada(Long jornadaId) {
        return partidoRepo.findByJornada_IdJornadaOrderByHoraInicioAsc(jornadaId).stream()
                .map(PartidoMapper::toResponse).toList();
    }

    public PartidoResponse getById(Long id) {
        return PartidoMapper.toResponse(findById(id));
    }

    @Transactional
    public PartidoResponse crear(PartidoRequest req) {
        if (req.equipoLocalId().equals(req.equipoVisitanteId())) {
            throw new BadRequestExeption("Un equipo no puede jugar contra sí mismo");
        }
        Equipo local     = equipoService.findById(req.equipoLocalId());
        Equipo visitante = equipoService.findById(req.equipoVisitanteId());
        Jornada jornada  = jornadaService.findById(req.jornadaId());

        Partido partido = Partido.builder()
                .equipoLocal(local)
                .equipoVisitante(visitante)
                .horaInicio(req.horaInicio())
                .jornada(jornada)
                .build();
        Partido guardado = partidoRepo.save(partido);
        jornadaService.actualizarEstadoAutomatico(jornada.getIdJornada());
        return PartidoMapper.toResponse(guardado);
    }

    @Transactional
    public PartidoResponse actualizar(Long id, PartidoRequest req) {
        Partido partido = findById(id);
        if (partido.getEstadoPartido() != EstadoPartido.PROGRAMADO) {
            throw new BadRequestExeption("Solo se puede modificar un partido PROGRAMADO");
        }
        if (req.equipoLocalId().equals(req.equipoVisitanteId())) {
            throw new BadRequestExeption("Un equipo no puede jugar contra sí mismo");
        }
        Equipo local     = equipoService.findById(req.equipoLocalId());
        Equipo visitante = equipoService.findById(req.equipoVisitanteId());
        Jornada jornada  = jornadaService.findById(req.jornadaId());
        Long jornadaAnteriorId = partido.getJornada().getIdJornada();

        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setHoraInicio(req.horaInicio());
        partido.setJornada(jornada);
        Partido guardado = partidoRepo.save(partido);

        jornadaService.actualizarEstadoAutomatico(jornadaAnteriorId);
        if (!jornadaAnteriorId.equals(jornada.getIdJornada())) {
            jornadaService.actualizarEstadoAutomatico(jornada.getIdJornada());
        }

        return PartidoMapper.toResponse(guardado);
    }

    @Transactional
    public PartidoResponse iniciarPartido(Long id) {
        Partido partido = findById(id);
        if (partido.getEstadoPartido() != EstadoPartido.PROGRAMADO) {
            throw new BadRequestExeption("El partido no está en estado PROGRAMADO");
        }
        partido.iniciarPartido();
        Partido guardado = partidoRepo.save(partido);
        jornadaService.actualizarEstadoAutomatico(partido.getJornada().getIdJornada());
        return PartidoMapper.toResponse(guardado);
    }

    @Transactional
    public PartidoResponse finalizarPartido(Long id, FinalizarPartidoRequest req) {
        Partido partido = findById(id);
        if (partido.getEstadoPartido() != EstadoPartido.EN_JUEGO) {
            throw new BadRequestExeption("Solo se puede finalizar un partido EN_JUEGO");
        }

        partido.finalizarPartido(req.golesLocal(), req.golesVisitante());
        partidoRepo.save(partido);

        prediccionService.calcularPuntosParaPartido(partido);
        jornadaService.actualizarEstadoAutomatico(partido.getJornada().getIdJornada());

        return PartidoMapper.toResponse(partido);
    }

    @Transactional
    public void eliminar(Long id) {
        Partido partido = findById(id);
        Long jornadaId = partido.getJornada().getIdJornada();

        if (partido.getEstadoPartido() != EstadoPartido.PROGRAMADO) {
            throw new BadRequestExeption("Solo se puede eliminar un partido PROGRAMADO");
        }
        if (prediccionRepo.existsByPartido_IdPartido(id)) {
            throw new BadRequestExeption("No se puede eliminar un partido con predicciones");
        }

        partidoRepo.delete(partido);
        jornadaService.actualizarEstadoAutomatico(jornadaId);
    }

    public Partido findById(Long id) {
        return partidoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partido", id));
    }
}
