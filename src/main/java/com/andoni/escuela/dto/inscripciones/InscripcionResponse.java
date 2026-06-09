package com.andoni.escuela.dto.inscripciones;

import java.time.LocalDate;

public record InscripcionResponse(
        Long id,
        Long alumnoId,
        Long grupoId,
        LocalDate fechaInscripcion
        // CalificacionResponse calificacion (opcional)
) {
}
