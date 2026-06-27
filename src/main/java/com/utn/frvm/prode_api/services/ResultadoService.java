package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.responsedto.ResultadoResponse;
import com.utn.frvm.prode_api.mapper.ResultadoMapper;
import com.utn.frvm.prode_api.models.Partido;
import com.utn.frvm.prode_api.models.Prediccion;
import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.repositories.PartidoRepository;
import com.utn.frvm.prode_api.repositories.PrediccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ResultadoService {

    private final PartidoRepository    partidoRepo;
    private final PrediccionRepository prediccionRepo;

    @Transactional(readOnly = true)
    public List<ResultadoResponse> getResultados(Long idUsuario, Long idJornada) {
        List<Partido> partidos;

        if (idJornada != null) {
            partidos = partidoRepo.findByJornada_IdJornadaAndEstadoPartidoOrderByHoraInicioAsc(
                    idJornada, EstadoPartido.FINALIZADO);
        } else {
            partidos = partidoRepo.findByEstadoPartidoOrderByHoraInicioAsc(EstadoPartido.FINALIZADO);
        }

        return partidos.stream().map(p -> {
            Optional<Prediccion> miPrediccion =
                    prediccionRepo.findByUsuario_IdUsuarioAndPartido_IdPartido(idUsuario, p.getIdPartido());
            return ResultadoMapper.toResponse(p, miPrediccion.orElse(null));
        }).toList();
    }
}
