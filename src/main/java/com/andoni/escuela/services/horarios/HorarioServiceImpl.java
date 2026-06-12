package com.andoni.escuela.services.horarios;

import com.andoni.escuela.dto.horarios.HorarioRequest;
import com.andoni.escuela.dto.horarios.HorarioResponse;
import com.andoni.escuela.entities.Grupo;
import com.andoni.escuela.entities.Horario;
import com.andoni.escuela.mappers.HorarioMapper;
import com.andoni.escuela.repositories.GrupoRepository;
import com.andoni.escuela.repositories.HorarioRepository;
import com.andoni.escuela.utils.ServiceUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HorarioServiceImpl implements HorarioService{

    private final HorarioRepository horarioRepository;
    private final HorarioMapper horarioMapper;
    private final GrupoRepository grupoRepository;

    @Override
    public List<HorarioResponse> listar() {
        log.info("Listado de todos los horarios solicitado");
        return horarioRepository.findAll()
                .stream()
                .map(horarioMapper::entidadAResponse)
                .toList();
    }

    @Override
    public HorarioResponse obtenerPorId(Long id) {
        return horarioMapper.entidadAResponse(obtenerHorario(id));
    }

    @Override
    public HorarioResponse registar(HorarioRequest request) {
        log.info("Registrando nuevo horario...");

        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        validarHoraFinPosterior(request);
        validarTraslapes(request, grupo, -1L); // -1L = ningún registro excluido en registro nuevo

        Horario horario = horarioMapper.requestAEntidad(request, grupo);
        horarioRepository.save(horario);

        log.info("Nuevo horario registrado: {} {} - {}", request.dia(), request.horaInicio(), request.horaFin());
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        log.info("Actualizando horario con id: {}", id);

        Horario horario = obtenerHorario(id);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        validarHoraFinPosterior(request);
        validarTraslapes(request, grupo, id); // excluye el propio registro al validar

        horario.setGrupo(grupo);
        horario.setDia(request.dia().trim());
        horario.setHoraInicio(request.horaInicio().trim());
        horario.setHoraFin(request.horaFin().trim());

        log.info("Horario {} actualizado correctamente", id);
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        Horario horario = obtenerHorario(id);
        log.info("Eliminando horario {}...", id);
        horarioRepository.delete(horario);
        log.info("Horario {} eliminado correctamente", id);
    }

    private Horario obtenerHorario(Long id) {
        return ServiceUtils.obtenerEntidadOException(horarioRepository, id, Horario.class);
    }

    private void validarHoraFinPosterior(HorarioRequest request) {
        if (request.horaFin().compareTo(request.horaInicio()) <= 0) {
            throw new IllegalArgumentException(
                    "La hora de fin debe ser posterior a la hora de inicio"
            );
        }
    }

    private void validarTraslapes(HorarioRequest request, Grupo grupo, Long excludeId) {
        if (horarioRepository.existsTraslapeEnGrupo(
                grupo.getId(), request.dia(),
                request.horaInicio(), request.horaFin(), excludeId)) {
            throw new IllegalArgumentException(
                    "Ya existe un horario que se traslapa en este grupo para el día " + request.dia()
            );
        }
        if (horarioRepository.existsTraslapeEnAula(
                grupo.getAula().getId(), request.dia(),
                request.horaInicio(), request.horaFin(), excludeId)) {
            throw new IllegalArgumentException(
                    "Ya existe un horario que se traslapa en el aula para el día " + request.dia()
            );
        }
    }
}
