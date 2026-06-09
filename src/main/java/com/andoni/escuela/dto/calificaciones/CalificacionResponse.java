package com.andoni.escuela.dto.calificaciones;

import java.time.LocalDate;

public record CalificacionResponse(
        Long id,
        Long inscripcionId,           // Referencia plana por ahora
        Double calificacion,
        LocalDate fechaRegistro
) {
}
