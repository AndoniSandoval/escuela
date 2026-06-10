package com.andoni.escuela.dto.aulas;

public record AulaResponse(
        Long id,
        String nombre,
        Integer capacidad
        // List<GrupoResponse> grupos (se agregará después)
) {
}
