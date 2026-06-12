package com.andoni.escuela.dto.calificaciones;

import com.andoni.escuela.dto.datos.DatosInscripcion;

import java.math.BigDecimal;

public record CalificacionResponse(
        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificacion,
        String fechaRegistro
) {
}
