package com.andoni.escuela.mappers;

import com.andoni.escuela.dto.datos.DatosAlumno;
import com.andoni.escuela.dto.datos.DatosGrupoHorario;
import com.andoni.escuela.dto.inscripciones.InscripcionRequest;
import com.andoni.escuela.dto.inscripciones.InscripcionResponse;
import com.andoni.escuela.entities.Alumno;
import com.andoni.escuela.entities.Grupo;
import com.andoni.escuela.entities.Inscripcion;
import com.andoni.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Component
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion>{

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        if (request == null) return null;
        return Inscripcion.builder()
                .build();
    }

    public Inscripcion requestAEntidad(InscripcionRequest request, Alumno alumno, Grupo grupo) {
        if (request == null) return null;
        return Inscripcion.builder()
                .alumno(alumno)
                .grupo(grupo)
                .fechaInscripcion(java.time.LocalDate.now())
                .build();
    }

    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {
        if (entidad == null) return null;

        Alumno alumno = entidad.getAlumno();
        Grupo grupo = entidad.getGrupo();
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

        BigDecimal calificacionValor = entidad.getCalificacion() != null
                ? entidad.getCalificacion().getCalificacion()
                : null;

        return new InscripcionResponse(
                entidad.getId(),
                datosAlumno,
                datosGrupo,
                calificacionValor,
                entidad.getFechaInscripcion().format(FORMATO_FECHA)
        );
    }
}
