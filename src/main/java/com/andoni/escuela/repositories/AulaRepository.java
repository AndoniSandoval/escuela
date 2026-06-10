    package com.andoni.escuela.repositories;

    import com.andoni.escuela.entities.Aula;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface AulaRepository extends JpaRepository<Aula, Long> {
        boolean existsByNombreIgnoreCase(String nombre);
        boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    }
