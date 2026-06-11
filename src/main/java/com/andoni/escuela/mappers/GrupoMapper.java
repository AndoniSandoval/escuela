package com.andoni.escuela.mappers;

import com.andoni.escuela.dto.grupos.GrupoRequest;
import com.andoni.escuela.dto.grupos.GrupoResponse;
import com.andoni.escuela.entities.Aula;
import com.andoni.escuela.entities.Curso;
import com.andoni.escuela.entities.Grupo;
import com.andoni.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

@Component
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo>{

    @Override
    public Grupo requestAEntidad (GrupoRequest request) {
        if (request == null) return null;

        return Grupo.builder()
                .periodo(request.periodo().trim())
                .build();
    }

    //sobrecarga de metodo
    public Grupo requestAEntidad(GrupoRequest request, Curso curso, Maestro maestro, Aula aula) {
        if (request == null) return null;
        return Grupo.builder()
                .periodo(request.periodo().trim())
                .curso(curso)
                .maestro(maestro)
                .aula(aula)
                .build();
    }

    @Override
    public GrupoResponse entidadAResponse(Grupo entidad){
        if (entidad == null) return null;

        return new GrupoResponse(
                entidad.getId(),
                entidad.getCurso().getId(),
                entidad.getMaestro().getId(),
                entidad.getAula().getId(),
                entidad.getPeriodo()
        );
    }
}
