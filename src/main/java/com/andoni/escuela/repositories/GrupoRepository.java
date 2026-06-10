package com.andoni.escuela.repositories;

import com.andoni.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    boolean existsByAulaId(Long idAula);
    boolean existsByMaestroId(Long idMaestro);
}
