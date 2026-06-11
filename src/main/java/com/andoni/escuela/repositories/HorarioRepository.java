package com.andoni.escuela.repositories;

import com.andoni.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    boolean existsByGrupoId(Long grupoId);
}
