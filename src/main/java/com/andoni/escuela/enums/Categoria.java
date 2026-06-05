package com.andoni.escuela.enums;

import com.andoni.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Categoria {
    LUNES ("Lunes"),
    MARTES ("Martes"),
    MIERCOLES ("Miercoles"),
    JUEVES ("Jueves"),
    VIERNES ("Viernes"),
    SABADO ("Sabado");

    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descripcionNormalizada = StringCustomUtils.quitarTildes(descripcion.trim());

        for (DiaSemana diaSemana: values()) {
            if (StringCustomUtils.quitarTildes(diaSemana.descripcion).equalsIgnoreCase(descripcionNormalizada)) {
                return diaSemana;
            }
        }
        throw new IllegalArgumentException(
                "No existe una categoría con la descripción: " + descripcion
        );
    }
}
