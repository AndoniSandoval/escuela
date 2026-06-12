package com.andoni.escuela.repositories;

import com.andoni.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    boolean existsByGrupoId(Long grupoId);

    // mismo día, horas que se cruzan, excluyendo el propio registro al actualizar
    @Query("""
        SELECT COUNT(h) > 0 FROM Horario h
        WHERE h.grupo.id = :grupoId
        AND h.dia = :dia
        AND h.id <> :excludeId
        AND h.horaInicio < :horaFin
        AND h.horaFin > :horaInicio
    """)
    boolean existsTraslapeEnGrupo(
            @Param("grupoId") Long grupoId,
            @Param("dia") String dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin,
            @Param("excludeId") Long excludeId
    );

    // mismo día, horas que se cruzan en cualquier grupo que use esa aula
    @Query("""
        SELECT COUNT(h) > 0 FROM Horario h
        WHERE h.grupo.aula.id = :aulaId
        AND h.dia = :dia
        AND h.id <> :excludeId
        AND h.horaInicio < :horaFin
        AND h.horaFin > :horaInicio
    """)
    boolean existsTraslapeEnAula(
            @Param("aulaId") Long aulaId,
            @Param("dia") String dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin,
            @Param("excludeId") Long excludeId
    );
}
