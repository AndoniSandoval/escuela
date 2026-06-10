package com.andoni.escuela.entities;

import com.andoni.escuela.utils.StringCustomUtils;
import com.andoni.escuela.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@Table(name = "AULAS")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", length = 30, nullable = false, unique = true)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @Builder.Default
    @OneToMany(mappedBy = "aula")
    private List<Grupo> grupos = new ArrayList<>();

    public void actualizar(String nombre, Integer capacidad){
        validarDatos(nombre,capacidad);
    }

    public Aula(String nombre, Integer capacidad, List<Grupo> grupos) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.grupos = grupos;
    }

    public void validarDatos(String nombre, Integer capacidad) {
        StringCustomUtils.validarTamanio(nombre, 4, 50, "EL nombre es requerido y debe contener entre 4 y 50 caracteres");
        ValoresNumericosUtils.validarEnteroPositivo(capacidad, "La capacidad debe ser mayor a 0");
    }
}