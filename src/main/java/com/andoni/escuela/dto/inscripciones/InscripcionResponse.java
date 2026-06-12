package com.andoni.escuela.dto.inscripciones;

import com.andoni.escuela.dto.datos.DatosAlumno;
import com.andoni.escuela.dto.datos.DatosGrupoHorario;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        DatosAlumno alumno,
        DatosGrupoHorario grupo,
        BigDecimal calificacion,      // null si aún no existe
        String fechaInscripcion
) {
}
