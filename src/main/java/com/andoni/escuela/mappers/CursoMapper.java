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
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso> {

    private final CursoMapper cursoMapper;

    @Override
    public Curso requestAEntidad(CursoRequest request) {
        return null;
    }

    @Override
    public CursoResponse entidadAResponse(Curso entidad) {
        return null;
    }

    public DatosCurso entidadADatosCurso(Curso entidad) {
        if (entidad == null) return null;

        String descripcion = entidad.getDescripcion() == null ?
                "Sin descripcion" : entidad.getDescripcion();

        return  new DatosCurso(
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }

    private List<DatosCurso> entidadADatosCurso(Maestro entidad) {
        if (entidad == null) return List.of();

        return entidad.getGrupos().stream().map(grupo ->
                cursoMapper.entidadADatosCurso(grupo.getCurso())).toList();
    }
}
