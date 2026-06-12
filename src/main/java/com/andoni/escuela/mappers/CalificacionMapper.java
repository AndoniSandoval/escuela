package com.andoni.escuela.mappers;

import com.andoni.escuela.dto.calificaciones.CalificacionRequest;
import com.andoni.escuela.dto.calificaciones.CalificacionResponse;
import com.andoni.escuela.dto.datos.DatosAlumno;
import com.andoni.escuela.dto.datos.DatosGrupoHorario;
import com.andoni.escuela.dto.datos.DatosInscripcion;
import com.andoni.escuela.entities.*;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;

@Component
public class CalificacionMapper implements CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion> {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Calificacion requestAEntidad(CalificacionRequest request) {
        if (request == null) return null;
        return Calificacion.builder()
                .calificacion(request.calificacion())
                .fechaRegistro(LocalDate.now())
                .build();
    }

    public Calificacion requestAEntidad(CalificacionRequest request, Inscripcion inscripcion) {
        if (request == null) return null;
        return Calificacion.builder()
                .inscripcion(inscripcion)
                .calificacion(request.calificacion())
                .fechaRegistro(LocalDate.now())
                .build();
    }

    @Override
    public CalificacionResponse entidadAResponse(Calificacion entidad) {
        if (entidad == null) return null;

        Inscripcion inscripcion = entidad.getInscripcion();
        Alumno alumno = inscripcion.getAlumno();
        Grupo grupo = inscripcion.getGrupo();
        Maestro maestro = grupo.getMaestro();

        DatosAlumno datosAlumno = new DatosAlumno(
                String.join(" ", alumno.getNombre(),
                        alumno.getApellidoPaterno(),
                        alumno.getApellidoMaterno()),
                alumno.getMatricula(),
                alumno.getEmail(),
                alumno.getFechaIngreso().format(FORMATO_FECHA)
        );

        DatosGrupoHorario datosGrupo = new DatosGrupoHorario(
                grupo.getCurso().getNombre(),
                String.join(" ", maestro.getNombre(),
                        maestro.getApellidoPaterno(),
                        maestro.getApellidoMaterno()),
                grupo.getAula().getNombre(),
                grupo.getPeriodo()
        );

        DatosInscripcion datosInscripcion = new DatosInscripcion(
                datosAlumno,
                datosGrupo,
                inscripcion.getFechaInscripcion().format(FORMATO_FECHA)
        );

        return new CalificacionResponse(
                entidad.getId(),
                datosInscripcion,
                entidad.getCalificacion(),
                entidad.getFechaRegistro().format(FORMATO_FECHA)
        );
    }
}
