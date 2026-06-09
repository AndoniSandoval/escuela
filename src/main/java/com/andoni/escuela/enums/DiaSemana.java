package com.andoni.escuela.enums;

import com.andoni.escuela.exceptions.RecursoNoEncontradoException;
import com.andoni.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {
    LUNES("LUNES"),
    MARTES("MARTES"),
    MIERCOLES("MIERCOLES"),
    JUEVES("JUEVES"),
    VIERNES("VIERNES"),
    SABADO("SABADO");

    private final String descripcion;

    public static DiaSemana obtenerCategoriaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La dexcripcion es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarTildes(descripcion.trim());
        for (DiaSemana diaSemana : values()) {
            if (StringCustomUtils.quitarTildes(diaSemana.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return diaSemana;

        }
        throw new RecursoNoEncontradoException("No existe una categoria con la descripcion:" + descripcion);
    }
}