package com.andoni.escuela.dto.maestros;

public record MaestroResponse(
        Long id,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String email,
        String telefono
        // List<GrupoResponse> grupos (se agregará después)
) {
}
