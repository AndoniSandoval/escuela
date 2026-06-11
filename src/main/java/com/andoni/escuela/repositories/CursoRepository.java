package com.andoni.escuela.repositories;

import com.andoni.escuela.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    boolean existsByCursoId(Long grupoId);
}
