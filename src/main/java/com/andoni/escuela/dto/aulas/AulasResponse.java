package com.andoni.escuela.dto.aulas;

public record AulasResponse(
        Long id,
        String nombre,
        Integer capacidad
        // List<GrupoResponse> grupos (se agregará después)
) {
}
