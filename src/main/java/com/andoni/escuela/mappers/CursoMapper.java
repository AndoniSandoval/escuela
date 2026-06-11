package com.andoni.escuela.mappers;

import com.andoni.escuela.dto.cursos.CursoRequest;
import com.andoni.escuela.dto.cursos.CursoResponse;
import com.andoni.escuela.dto.datos.DatosCurso;
import com.andoni.escuela.entities.Curso;
import com.andoni.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso>{

    @Override
    public Curso requestAEntidad(CursoRequest request) {
        if (request == null) return null;

        return Curso.builder()
                .nombre(request.nombre().trim())
                .descripcion(request.descripcion().trim())
                .creditos(request.creditos())
                .build();
    }

    @Override
    public CursoResponse entidadAResponse(Curso entidad) {
        if (entidad == null) return null;

        return new CursoResponse(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getDescripcion(),
                entidad.getCreditos()
        );
    }

    public DatosCurso entidadADatosCurso(Curso curso) {
        if(curso == null) return null;

        String descripcion = curso.getDescripcion() == null ? "Sin descripcion" : curso.getDescripcion();

        return new DatosCurso(
                curso.getNombre(),
                descripcion,
                curso.getCreditos());
    }
}