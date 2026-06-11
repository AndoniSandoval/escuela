package com.andoni.escuela.entities;

import com.andoni.escuela.utils.StringCustomUtils;
import com.andoni.escuela.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@Table(name = "CURSOS")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;

    @Builder.Default
    @OneToMany(mappedBy = "curso")
    private List<Grupo> grupos = new ArrayList<>();

    public void actualizar(String nombre, String descripcion, Integer creditos){
        validarDatos(nombre,descripcion, creditos);

        this.nombre = nombre.trim();
        this.descripcion = descripcion.trim();
        this.creditos =  creditos;
    }

    public void validarDatos(String nombre, String descripcion, Integer creditos) {
        StringCustomUtils.validarTamanio(nombre, 4, 50, "EL nombre es requerido y debe contener entre 4 y 100 caracteres");
        StringCustomUtils.validarTamanio(descripcion,4,200, "L nombre es requerido y debe contener entre 4 y 200 caracteres ");
        ValoresNumericosUtils.validarEnteroPositivo(creditos, "Los creditos deben ser mayor a 0");
    }
}
