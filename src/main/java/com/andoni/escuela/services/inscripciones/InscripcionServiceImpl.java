package com.andoni.escuela.services.inscripciones;


import com.andoni.escuela.dto.inscripciones.InscripcionRequest;
import com.andoni.escuela.dto.inscripciones.InscripcionResponse;
import com.andoni.escuela.entities.Alumno;
import com.andoni.escuela.entities.Grupo;
import com.andoni.escuela.entities.Inscripcion;
import com.andoni.escuela.exceptions.EntidadRelacionadaException;
import com.andoni.escuela.mappers.InscripcionMapper;
import com.andoni.escuela.repositories.AlumnoRepository;
import com.andoni.escuela.repositories.GrupoRepository;
import com.andoni.escuela.repositories.InscripcionRespository;
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
public class InscripcionServiceImpl implements InscripcionService{

    private final InscripcionRespository inscripcionRepository;
    private final InscripcionMapper inscripcionMapper;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;

    @Override
    public List<InscripcionResponse> listar() {
        log.info("Listado de todas las inscripciones solicitado");
        return inscripcionRepository.findAll()
                .stream()
                .map(inscripcionMapper::entidadAResponse)
                .toList();
    }

    @Override
    public InscripcionResponse obtenerPorId(Long id) {
        return inscripcionMapper.entidadAResponse(obtenerInscripcion(id));
    }

    @Override
    public InscripcionResponse registar(InscripcionRequest request) {
        log.info("Registrando nueva inscripción...");

        Alumno alumno = ServiceUtils.obtenerEntidadOException(
                alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        validarUnicidad(request.idAlumno(), request.idGrupo());

        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request, alumno, grupo);
        inscripcionRepository.save(inscripcion);

        log.info("Nueva inscripción registrada — alumno: {}, grupo: {}",
                alumno.getMatricula(), grupo.getId());
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {
        log.info("Actualizando inscripción con id: {}", id);

        Inscripcion inscripcion = obtenerInscripcion(id);

        Alumno alumno = ServiceUtils.obtenerEntidadOException(
                alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository, request.idGrupo(), Grupo.class);

        validarUnicidadAlActualizar(request.idAlumno(), request.idGrupo(), id);

        inscripcion.setAlumno(alumno);
        inscripcion.setGrupo(grupo);

        log.info("Inscripción {} actualizada correctamente", id);
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);

        log.info("Eliminando inscripción {}...", id);

        if (inscripcionRepository.existsByIdAndCalificacionIsNotNull(id)) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar la inscripción ya que tiene una calificación asociada"
            );
        }

        inscripcionRepository.delete(inscripcion);
        log.info("Inscripción {} eliminada correctamente", id);
    }

    private Inscripcion obtenerInscripcion(Long id) {
        return ServiceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }

    private void validarUnicidad(Long idAlumno, Long idGrupo) {
        if (inscripcionRepository.existsByAlumnoIdAndGrupoId(idAlumno, idGrupo)) {
            throw new IllegalArgumentException(
                    "El alumno ya está inscrito en este grupo"
            );
        }
    }

    private void validarUnicidadAlActualizar(Long idAlumno, Long idGrupo, Long id) {
        if (inscripcionRepository.existsByAlumnoIdAndGrupoIdAndIdNot(idAlumno, idGrupo, id)) {
            throw new IllegalArgumentException(
                    "El alumno ya está inscrito en este grupo"
            );
        }
    }
}
