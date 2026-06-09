package com.andoni.escuela.dto.grupos;

public record GrupoResponse(
        Long id,
        Long cursoId,
        Long maestroId,
        Long aulaId,
        String periodo
        // List<HorarioResponse> horarios
        // List<InscripcionResponse> inscripciones
) {
}
