package com.andoni.escuela.dto.inscripciones;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InscripcionRequest(
        @NotNull(message = "El alumno es requerido")
        Long alumnoId,

        @NotNull(message = "El grupo es requerido")
        Long grupoId,

        LocalDate fechaInscripcion
) {
}
