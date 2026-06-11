package com.andoni.escuela.repositories;

import com.andoni.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRespository extends JpaRepository<Inscripcion, Long> {

    boolean existsByGrupoId(Long grupoId);
}
