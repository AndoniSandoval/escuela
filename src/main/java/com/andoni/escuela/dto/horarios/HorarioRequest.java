package com.andoni.escuela.dto.horarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HorarioRequest(
        @NotNull(message = "El grupo es requerido")
        Long grupoId,

        @NotBlank(message = "El día es requerido")
        @Size(max = 15, message = "El día no puede exceder 15 caracteres")
        String dia,

        @NotBlank(message = "La hora de inicio es requerida")
        @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Formato de hora inválido (HH:mm)")
        String horaInicio,

        @NotBlank(message = "La hora de fin es requerida")
        @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Formato de hora inválido (HH:mm)")
        String horaFin
) {
}
