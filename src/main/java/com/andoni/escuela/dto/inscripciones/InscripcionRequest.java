package com.andoni.escuela.dto.inscripciones;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InscripcionRequest(
        @NotNull(message = "El alumno es requerido")
        Long idAlumno,

        @NotNull(message = "El grupo es requerido")
        Long idGrupo
) {
}
