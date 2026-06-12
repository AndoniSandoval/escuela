package com.andoni.escuela.dto;

import java.math.BigDecimal;

public record ResumenCalificacion(
        String curso,
        String periodo,
        BigDecimal calificacion
) {
}
