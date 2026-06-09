package com.andoni.escuela.dto.alumnos;

import java.time.LocalDate;

public record AlumnosResponse(
        Long id,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String email,
        String matricula,
        LocalDate fechaIngreso
) {
}
