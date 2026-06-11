package com.andoni.escuela.repositories;

import com.andoni.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    boolean existsByPeriodoAndIdNot(String periodo, Long id);
    boolean existsByPeriodo(String periodo);
    boolean existsByAulaId(Long idAula);
    boolean existsByMaestroId(Long idMaestro);
}
