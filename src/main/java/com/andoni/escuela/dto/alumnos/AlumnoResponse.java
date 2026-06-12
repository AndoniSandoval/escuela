package com.andoni.escuela.dto.alumnos;

import com.andoni.escuela.dto.ResumenCalificacion;

import java.math.BigDecimal;
import java.util.List;

public record AlumnoResponse(
        Long id,
        String nombre,
        String email,
        String matricula,
        String fechaIngreso,
        List<ResumenCalificacion> calificaciones,
        BigDecimal promedio
) {
}
