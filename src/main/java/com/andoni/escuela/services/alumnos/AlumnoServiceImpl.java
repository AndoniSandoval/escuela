package com.andoni.escuela.services.alumnos;

import com.andoni.escuela.dto.alumnos.AlumnoRequest;
import com.andoni.escuela.dto.alumnos.AlumnoResponse;
import com.andoni.escuela.entities.Alumno;
import com.andoni.escuela.exceptions.EntidadRelacionadaException;
import com.andoni.escuela.mappers.AlumnoMapper;
import com.andoni.escuela.repositories.AlumnoRepository;
import com.andoni.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;
    private final DataSource dataSource;

    @Override
    public List<AlumnoResponse> listar() {
        log.info("Listado de todos los alumnos solicitado");
        return alumnoRepository.findAll()
                .stream()
                .map(alumnoMapper::entidadAResponse)
                .toList();
    }

    @Override
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnoResponse registar(AlumnoRequest request) {
        log.info("Registrando nuevo alumno...");

        Alumno alumno = alumnoMapper.requestAEntidad(request);

        String matricula = ejecutarGenerarMatricula(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno()
        );
        String email = ejecutarGenerarCorreo(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno()
        );

        alumno.setMatricula(matricula);
        alumno.setEmail(email);

        alumnoRepository.save(alumno);

        log.info("Nuevo alumno registrado con matrícula {}", alumno.getMatricula());
        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        log.info("Actualizando alumno con id: {}", id);

        Alumno alumno = obtenerAlumno(id);
        alumno.setNombre(request.nombre().trim());
        alumno.setApellidoPaterno(request.apellidoPaterno().trim());
        alumno.setApellidoMaterno(request.apellidoMaterno().trim());

        String matricula = ejecutarGenerarMatricula(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno()
        );
        String email = ejecutarGenerarCorreo(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno()
        );

        alumno.setMatricula(matricula);
        alumno.setEmail(email);

        log.info("Alumno {} actualizado correctamente", alumno.getMatricula());
        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Eliminando alumno {}...", alumno.getMatricula());

        if (alumnoRepository.existsByInscripcionesId(id)) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el alumno ya que tiene inscripciones asociadas"
            );
        }

        alumnoRepository.delete(alumno);
        log.info("Alumno {} eliminado correctamente", alumno.getMatricula());
    }

    private Alumno obtenerAlumno(Long id) {
        return ServiceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }

    private String ejecutarGenerarMatricula(String nombre, String apellidoPaterno, String apellidoMaterno) {
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall("{ ? = call GENERAR_MATRICULA(?, ?, ?) }")) {

            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setString(2, nombre);
            cs.setString(3, apellidoPaterno);
            cs.setString(4, apellidoMaterno);
            cs.execute();

            return cs.getString(1);

        } catch (Exception e) {
            log.error("Error al ejecutar GENERAR_MATRICULA: {}", e.getMessage());
            throw new IllegalStateException("No se pudo generar la matrícula del alumno", e);
        }
    }

    private String ejecutarGenerarCorreo(String nombre, String apellidoPaterno, String apellidoMaterno) {
        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall("{ ? = call GENERAR_CORREO(?, ?, ?) }")) {

            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setString(2, nombre);
            cs.setString(3, apellidoPaterno);
            cs.setString(4, apellidoMaterno);
            cs.execute();

            return cs.getString(1);

        } catch (Exception e) {
            log.error("Error al ejecutar GENERAR_CORREO: {}", e.getMessage());
            throw new IllegalStateException("No se pudo generar el email del alumno", e);
        }
    }
}