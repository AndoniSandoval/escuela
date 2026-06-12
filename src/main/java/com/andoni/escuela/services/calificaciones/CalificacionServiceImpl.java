package com.andoni.escuela.services.calificaciones;


import com.andoni.escuela.dto.calificaciones.CalificacionRequest;
import com.andoni.escuela.dto.calificaciones.CalificacionResponse;
import com.andoni.escuela.entities.Calificacion;
import com.andoni.escuela.entities.Inscripcion;
import com.andoni.escuela.mappers.CalificacionMapper;
import com.andoni.escuela.repositories.CalificacionRepository;
import com.andoni.escuela.repositories.InscripcionRespository;
import com.andoni.escuela.utils.ServiceUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CalificacionServiceImpl implements CalificacionService{

    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;
    private final InscripcionRespository inscripcionRepository;


    @Override
    public List<CalificacionResponse> listar() {
        log.info("Listado de todas las calificaciones solicitado");
        return calificacionRepository.findAll()
                .stream()
                .map(calificacionMapper::entidadAResponse)
                .toList();
    }

    @Override
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entidadAResponse(obtenerCalificacion(id));
    }

    @Override
    public CalificacionResponse registar(CalificacionRequest request) {
        log.info("Registrando nueva calificación...");

        Inscripcion inscripcion = ServiceUtils.obtenerEntidadOException(
                inscripcionRepository, request.idInscripcion(), Inscripcion.class);

        if (calificacionRepository.existsByInscripcionId(request.idInscripcion())) {
            throw new IllegalArgumentException(
                    "Ya existe una calificación registrada para esta inscripción"
            );
        }

        Calificacion calificacion = calificacionMapper.requestAEntidad(request, inscripcion);
        calificacionRepository.save(calificacion);

        log.info("Nueva calificación registrada para inscripción {}", request.idInscripcion());
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        log.info("Actualizando calificación con id: {}", id);

        Calificacion calificacion = obtenerCalificacion(id);

        if (calificacionRepository.existsByInscripcionIdAndIdNot(request.idInscripcion(), id)) {
            throw new IllegalArgumentException(
                    "Ya existe una calificación registrada para esta inscripción"
            );
        }

        Inscripcion inscripcion = ServiceUtils.obtenerEntidadOException(
                inscripcionRepository, request.idInscripcion(), Inscripcion.class);

        calificacion.setInscripcion(inscripcion);
        calificacion.setCalificacion(request.calificacion());

        log.info("Calificación {} actualizada correctamente", id);
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        Calificacion calificacion = obtenerCalificacion(id);
        log.info("Eliminando calificación {}...", id);
        calificacionRepository.delete(calificacion);
        log.info("Calificación {} eliminada correctamente", id);
    }

    private Calificacion obtenerCalificacion(Long id) {
        return ServiceUtils.obtenerEntidadOException(calificacionRepository, id, Calificacion.class);
    }
}
