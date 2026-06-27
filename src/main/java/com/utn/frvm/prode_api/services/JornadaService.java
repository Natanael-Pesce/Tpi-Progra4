package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.requestdto.JornadaRequest;
import com.utn.frvm.prode_api.dtos.responsedto.JornadaResponse;
import com.utn.frvm.prode_api.dtos.responsedto.PartidoResponse;
import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.config.exeptions.ResourceNotFoundException;
import com.utn.frvm.prode_api.mapper.JornadaMapper;
import com.utn.frvm.prode_api.mapper.PartidoMapper;
import com.utn.frvm.prode_api.models.Jornada;
import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.utility.EstadoJornada;
import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.repositories.JornadaRepository;
import com.utn.frvm.prode_api.repositories.PartidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JornadaService {

    private final JornadaRepository jornadaRepo;
    private final PartidoRepository partidoRepo;

    @Transactional(readOnly = true)
    public List<JornadaResponse> listarTodas() {
        return jornadaRepo.findAllByOrderByFechaInicioAsc().stream()
                .map(JornadaMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public JornadaResponse getById(Long id) {
        return JornadaMapper.toResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<PartidoResponse> getPartidosPorJornada(Long idJornada) {
        findById(idJornada);
        return partidoRepo.findByJornada_IdJornadaOrderByHoraInicioAsc(idJornada).stream()
                .map(PartidoMapper::toResponse).toList();
    }

    @Transactional
    public JornadaResponse crear(JornadaRequest req) {
        if (req.fechaFin().isBefore(req.fechaInicio())) {
            throw new BadRequestExeption("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
        if (jornadaRepo.findByNombreIgnoreCase(req.nombre()).isPresent()) {
            throw new BadRequestExeption("Ya existe una jornada con ese nombre");
        }
        Jornada jornada = Jornada.builder()
                .nombre(req.nombre())
                .fechaInicio(req.fechaInicio())
                .fechaFin(req.fechaFin())
                .estadoJornada(EstadoJornada.PROGRAMADA)
                .build();
        return JornadaMapper.toResponse(jornadaRepo.save(jornada));
    }

    @Transactional
    public JornadaResponse actualizar(Long id, JornadaRequest req) {
        Jornada jornada = findById(id);
        if (req.fechaFin().isBefore(req.fechaInicio())) {
            throw new BadRequestExeption("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
        if (jornada.getEstadoJornada() != EstadoJornada.PROGRAMADA) {
            throw new BadRequestExeption("Solo se puede modificar una jornada PROGRAMADA");
        }
        if (partidoRepo.existsByJornada_IdJornada(id)) {
            throw new BadRequestExeption("No se puede modificar una jornada con partidos asociados");
        }
        if (jornadaRepo.findByNombreIgnoreCase(req.nombre())
                .filter(j -> !j.getIdJornada().equals(id))
                .isPresent()) {
            throw new BadRequestExeption("Ya existe una jornada con ese nombre");
        }
        jornada.setNombre(req.nombre());
        jornada.setFechaInicio(req.fechaInicio());
        jornada.setFechaFin(req.fechaFin());
        return JornadaMapper.toResponse(jornadaRepo.save(jornada));
    }

    @Transactional
    public void eliminar(Long id) {
        Jornada jornada = findById(id);
        if (jornada.getEstadoJornada() != EstadoJornada.PROGRAMADA) {
            throw new BadRequestExeption("Solo se puede eliminar una jornada PROGRAMADA");
        }
        if (partidoRepo.existsByJornada_IdJornada(id)) {
            throw new BadRequestExeption("No se puede eliminar una jornada con partidos asociados");
        }
        jornadaRepo.delete(jornada);
    }

    @Transactional
    public void actualizarEstadoAutomatico(Long jornadaId) {
        Jornada jornada = findById(jornadaId);
        List<Partido> partidos = partidoRepo.findByJornada_IdJornadaOrderByHoraInicioAsc(jornadaId);
        EstadoJornada nuevoEstado = calcularEstado(partidos);

        if (jornada.getEstadoJornada() != nuevoEstado) {
            jornada.setEstadoJornada(nuevoEstado);
            jornadaRepo.save(jornada);
        }
    }

    private EstadoJornada calcularEstado(List<Partido> partidos) {
        if (partidos.isEmpty()) {
            return EstadoJornada.PROGRAMADA;
        }

        boolean todosProgramados = partidos.stream()
                .allMatch(p -> p.getEstadoPartido() == EstadoPartido.PROGRAMADO);
        if (todosProgramados) {
            return EstadoJornada.PROGRAMADA;
        }

        boolean todosFinalizados = partidos.stream()
                .allMatch(p -> p.getEstadoPartido() == EstadoPartido.FINALIZADO);
        return todosFinalizados ? EstadoJornada.FINALIZADA : EstadoJornada.EN_JUEGO;
    }

    public Jornada findById(Long id) {
        return jornadaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jornada", id));
    }
}
