package com.andoni.escuela.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@Table(name = "INSCRIPCIONES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ID_ALUMNO", "ID_GRUPO"})
})
public class Inscripciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUMNO", nullable = false)
    private Alumnos alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupos grupo;

    @Column(name = "FECHA_INSCRIPCION", nullable = false)
    private LocalDate fechaInscripcion;

}