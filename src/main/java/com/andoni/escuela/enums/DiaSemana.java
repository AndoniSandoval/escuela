package com.andoni.escuela.enums;

import com.andoni.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {
    LUNES ("Lunes"),
    MARTES ("Martes"),
    MIERCOLES ("Miercoles"),
    JUEVES ("Jueves"),
    VIERNES ("Viernes"),
    SABADO ("Sabado");

    private final String descripcion;

    public static DiaSemana obtenerCategoriaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La dexcripcion es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarTildes(descripcion.trim());
        for (DiaSemana diaSemana : values()) {
            if (StringCustomUtils.quitarTildes(diaSemana.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return diaSemana;

        }
        throw new IllegalArgumentException("No existe una categoria con la descripcion" );
    }
}
