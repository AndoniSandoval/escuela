package com.andoni.escuela.dto.aulas;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AulaRequest(
        @NotBlank(message = "El nombre del aula es requerido")
        @Size(min = 3, max = 30, message = "El nombre debe tener entre 3 y 30 caracteres")
        String nombre,

        @NotNull(message = "La capacidad es requerida")
        @Min(value = 1, message = "La capacidad debe ser mayor a 0")
        Integer capacidad
) {
}
