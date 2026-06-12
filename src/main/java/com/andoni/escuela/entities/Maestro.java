package com.andoni.escuela.entities;

import com.andoni.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "MAESTROS")
public class Maestro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MAESTRO")
    private Long id;

    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 50, nullable = false)
    private String apellidoMaterno;

    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "TELEFONO", length = 10, nullable = false, unique = true)
    private String telefono;

    @Builder.Default
    @OneToMany(mappedBy = "maestro")
    private List<Grupo> grupos = new ArrayList<>();

    public void actualizar(String nombre, String apellidoPaterno,
                                                   String apellidoMaterno, String email, String telefono){
        validarDatos(nombre,apellidoPaterno, apellidoMaterno, email, telefono);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
        this.email = email.trim();
        this.telefono = telefono.trim();
    }

    public Maestro(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
    }

    public void validarDatos(String nombre, String apellidoPaterno,
                              String apellidoMaterno, String email, String telefono){
        StringCustomUtils.validarTamanio(nombre, 4, 50, "EL nombre es requerido y debe contener entre 4 y 50 caracteres");
        StringCustomUtils.validarTamanio(apellidoPaterno, 4, 50, "EL apellido paterno es requerido y debe contener entre 4 y 50 caracteres");
        StringCustomUtils.validarTamanio(apellidoMaterno, 4, 50, "EL apellido materno es requerido y debe contener entre 4 y 50 caracteres");
        StringCustomUtils.validarTamanio(email, 8, 100, "EL email es requerido y debe contener entre 8 y 100 caracteres");
        StringCustomUtils.validarTamanio(telefono, 10, 10, "EL telefono es requerido y debe contener exactamente 10 caracteres");
    }
}