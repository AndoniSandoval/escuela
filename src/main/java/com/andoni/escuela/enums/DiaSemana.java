package com.andoni.escuela.enums;

<<<<<<< HEAD
=======
import com.andoni.escuela.exceptions.RecursoNoEncontradoException;
>>>>>>> 06c84cc90f123b47cfdf9557ca1d6c506c884b43
import com.andoni.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {
<<<<<<< HEAD
    LUNES ("Lunes"),
    MARTES ("Martes"),
    MIERCOLES ("Miercoles"),
    JUEVES ("Jueves"),
    VIERNES ("Viernes"),
    SABADO ("Sabado");
=======
    LUNES("LUNES"),
    MARTES("MARTES"),
    MIERCOLES("MIERCOLES"),
    JUEVES("JUEVES"),
    VIERNES("VIERNES"),
    SABADO("SABADO");
>>>>>>> 06c84cc90f123b47cfdf9557ca1d6c506c884b43

    private final String descripcion;

    public static DiaSemana obtenerCategoriaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La dexcripcion es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarTildes(descripcion.trim());
        for (DiaSemana diaSemana : values()) {
            if (StringCustomUtils.quitarTildes(diaSemana.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return diaSemana;

        }
<<<<<<< HEAD
        throw new IllegalArgumentException("No existe una categoria con la descripcion" );
    }
}
=======
        throw new RecursoNoEncontradoException("No existe una categoria con la descripcion:" + descripcion);
    }
}
>>>>>>> 06c84cc90f123b47cfdf9557ca1d6c506c884b43
