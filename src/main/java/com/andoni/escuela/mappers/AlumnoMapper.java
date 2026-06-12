package com.andoni.escuela.mappers;

import com.andoni.escuela.dto.ResumenCalificacion;
import com.andoni.escuela.dto.alumnos.AlumnoRequest;
import com.andoni.escuela.dto.alumnos.AlumnoResponse;
import com.andoni.escuela.dto.calificaciones.CalificacionResponse;
import com.andoni.escuela.entities.Alumno;
import com.andoni.escuela.entities.Inscripcion;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {
        if (request == null) return null;
        return Alumno.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .build();
    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {
        if (entidad == null) return null;

        List<ResumenCalificacion > calificaciones = entidad.getInscripciones()
                .stream()
                .map(this::inscripcionAResumenCalificacion )
                .toList();

        BigDecimal promedio = calcularPromedio(calificaciones);

        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ", entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                entidad.getFechaIngreso()!= null
                        ? entidad.getFechaIngreso().format(FORMATO_FECHA)
                        : LocalDate.now().format(FORMATO_FECHA),
                calificaciones,
                promedio
        );
    }

    private ResumenCalificacion  inscripcionAResumenCalificacion (Inscripcion inscripcion) {
        BigDecimal calificacionValor = inscripcion.getCalificacion() != null
                ? inscripcion.getCalificacion().getCalificacion()
                : null;

        return new ResumenCalificacion(
                inscripcion.getGrupo().getCurso().getNombre(),
                inscripcion.getGrupo().getPeriodo(),
                calificacionValor
        );
    }

    private BigDecimal calcularPromedio(List<ResumenCalificacion > calificaciones) {
        if (calificaciones == null || calificaciones.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        List<BigDecimal> conCalificacion = calificaciones.stream()
                .map(ResumenCalificacion ::calificacion)
                .filter(c -> c != null)
                .toList();

        if (conCalificacion.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal suma = conCalificacion.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return suma.divide(BigDecimal.valueOf(conCalificacion.size()), 2, RoundingMode.HALF_UP);
    }
}