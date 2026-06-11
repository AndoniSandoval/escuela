package com.andoni.escuela.dto.grupos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GrupoRequest(
        @NotNull(message = "El curso es requerido")
        Long idCurso,

        @NotNull(message = "El maestro es requerido")
        Long idMaestro,

        @NotNull(message = "El aula es requerida")
        Long idAula,

        @NotBlank(message = "El periodo es requerido")
        @Size(max = 20, message = "El periodo no puede exceder 20 caracteres")
        String periodo
) {
}
