package com.andoni.escuela.dto.horarios;

public record HorarioResponse(
        Long id,
        Long grupoId,
        String dia,
        String horaInicio,
        String horaFin
) {
}
