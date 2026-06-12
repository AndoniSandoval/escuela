package com.andoni.escuela.dto.datos;

public record DatosInscripcion (
        DatosAlumno alumno,
        DatosGrupoHorario grupo,
        String fechaInscripcion
) {
}
