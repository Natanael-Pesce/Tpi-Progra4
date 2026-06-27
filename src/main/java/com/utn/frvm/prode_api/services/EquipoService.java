package com.utn.frvm.prode_api.services;

import com.utn.frvm.prode_api.dtos.requestdto.EquipoRequest;
import com.utn.frvm.prode_api.dtos.responsedto.EquipoResponse;
import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.config.exeptions.ResourceNotFoundException;
import com.utn.frvm.prode_api.mapper.EquipoMapper;
import com.utn.frvm.prode_api.models.Equipo;
import com.utn.frvm.prode_api.repositories.EquipoRepository;
import com.utn.frvm.prode_api.repositories.PartidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepo;
    private final PartidoRepository partidoRepo;

    public List<EquipoResponse> listarActivos() {
        return equipoRepo.findByEstaActivoTrue().stream()
                .map(EquipoMapper::toResponse).toList();
    }

    public List<EquipoResponse> buscarPorNombre(String nombre) {
        return equipoRepo.findByNombreContainingIgnoreCaseAndEstaActivoTrue(nombre).stream()
                .map(EquipoMapper::toResponse).toList();
    }

    public EquipoResponse getById(Long id) {
        return EquipoMapper.toResponse(findById(id));
    }

    @Transactional
    public EquipoResponse crear(EquipoRequest req) {
        if (equipoRepo.findByNombreIgnoreCase(req.nombre()).isPresent()) {
            throw new BadRequestExeption("Ya existe un equipo con ese nombre");
        }
        Equipo equipo = Equipo.builder()
                .nombre(req.nombre())
                .estaActivo(req.estaActivo() != null ? req.estaActivo() : true)
                .build();
        return EquipoMapper.toResponse(equipoRepo.save(equipo));
    }

    @Transactional
    public EquipoResponse actualizar(Long id, EquipoRequest req) {
        Equipo equipo = findById(id);
        equipo.setNombre(req.nombre());
        if (req.estaActivo() != null) equipo.setEstaActivo(req.estaActivo());
        return EquipoMapper.toResponse(equipoRepo.save(equipo));
    }

    @Transactional
    public void eliminar(Long id) {
        Equipo equipo = findById(id);
        if (partidoRepo.existsByEquipoLocal_IdEquipoOrEquipoVisitante_IdEquipo(id, id)) {
            throw new BadRequestExeption("No se puede eliminar un equipo asociado a partidos");
        }
        equipoRepo.delete(equipo);
    }

    public Equipo findById(Long id) {
        return equipoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo", id));
    }
}
