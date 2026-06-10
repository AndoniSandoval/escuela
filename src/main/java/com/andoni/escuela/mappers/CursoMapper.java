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
        throw new UnsupportedOperationException("Unimplemented method 'requestAEntidad'");
    }

    @Override
    public CursoResponse entidadAResponse(Curso entidad) {
        throw new UnsupportedOperationException("Unimplemented method 'entidadAResponse'");
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