package com.andoni.escuela.repositories;

import com.andoni.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    boolean existsByInscripcionId(Long inscripcionId);
    boolean existsByInscripcionIdAndIdNot(Long inscripcionId, Long id);
}
