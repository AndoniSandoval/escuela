package com.andoni.escuela.dto.cursos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CursoRequest(
        @NotBlank(message = "El nombre del curso es requerido")
        @Size(min = 5, max = 100, message = "El nombre debe tener entre 5 y 100 caracteres")
        String nombre,

        @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
        String descripcion,

        @NotNull(message = "Los créditos son requeridos")
        @Min(value = 1, message = "Los créditos deben ser mayor a 0")
        Integer creditos
) {
}
