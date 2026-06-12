package com.andoni.escuela.dto.horarios;

import com.andoni.escuela.dto.datos.DatosGrupoHorario;

public record HorarioResponse(
        Long id,
        DatosGrupoHorario grupo,
        String horario
) {
}
