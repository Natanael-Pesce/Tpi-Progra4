package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.requestdto.EditarPerfilRequest;
import com.utn.frvm.prode_api.dtos.responsedto.EstadisticasResponse;
import com.utn.frvm.prode_api.dtos.responsedto.UsuarioResponse;
import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.config.exeptions.ResourceNotFoundException;
import com.utn.frvm.prode_api.mapper.UsuarioMapper;
import com.utn.frvm.prode_api.models.Prediccion;
import com.utn.frvm.prode_api.models.Usuario;
import com.utn.frvm.prode_api.utility.EstadoPartido;
import com.utn.frvm.prode_api.repositories.PrediccionRepository;
import com.utn.frvm.prode_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final PrediccionRepository prediccionRepo;

    @Transactional(readOnly = true)
    public UsuarioResponse getPerfil(Long idUsuario) {
        return UsuarioMapper.toResponse(findById(idUsuario));
    }

    @Transactional(readOnly = true)
    public UsuarioResponse getPerfilPublico(Long idUsuario) {
        return UsuarioMapper.toResponse(findById(idUsuario));
    }

    @Transactional
    public UsuarioResponse editarPerfil(Long idUsuario, EditarPerfilRequest req) {
        Usuario usuario = findById(idUsuario);

        if (!usuario.getCorreo().equalsIgnoreCase(req.correo())
                && usuarioRepo.existsByCorreo(req.correo())) {
            throw new BadRequestExeption("El correo ya esta registrado por otro usuario");
        }

        usuario.setNombre(req.nombre());
        usuario.setApellido(req.apellido());
        usuario.setCorreo(req.correo());

        return UsuarioMapper.toResponse(usuarioRepo.save(usuario));
    }

    @Transactional(readOnly = true)
    public EstadisticasResponse getEstadisticas(Long idUsuario) {
        Usuario usuario = findById(idUsuario);

        Long totalPredicciones = prediccionRepo.contarTotalPredicciones(idUsuario);
        Long aciertos = prediccionRepo.contarAciertos(idUsuario);
        double efectividad = totalPredicciones > 0
                ? aciertos * 100.0 / totalPredicciones
                : 0.0;

        List<Object[]> puntosPorJornada = prediccionRepo.evolucionPorJornada(idUsuario);
        List<EstadisticasResponse.EvolucionItem> evolucion = puntosPorJornada.stream()
                .map(fila -> new EstadisticasResponse.EvolucionItem(
                        ((Number) fila[0]).longValue(),
                        (String) fila[1],
                        ((Number) fila[2]).intValue()))
                .toList();

        List<Prediccion> predicciones = prediccionRepo.findByUsuario_IdUsuarioOrderByFechaCreacionAsc(idUsuario);
        int rachaActual = 0;
        int mejorRacha = 0;
        for (Prediccion prediccion : predicciones) {
            if (prediccion.getPuntosObtenidos() > 0) {
                rachaActual++;
                mejorRacha = Math.max(mejorRacha, rachaActual);
            } else if (prediccion.getPartido().getEstadoPartido() == EstadoPartido.FINALIZADO) {
                rachaActual = 0;
            }
        }

        return new EstadisticasResponse(
                idUsuario,
                usuario.getNombre() + " " + usuario.getApellido(),
                usuario.getPuntos(),
                totalPredicciones,
                aciertos,
                efectividad,
                mejorRacha,
                evolucion);
    }

    public Usuario findById(Long idUsuario) {
        return usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", idUsuario));
    }
}