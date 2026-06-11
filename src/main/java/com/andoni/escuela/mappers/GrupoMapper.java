package com.andoni.escuela.mappers;

import com.andoni.escuela.dto.datos.DatosAula;
import com.andoni.escuela.dto.datos.DatosCurso;
import com.andoni.escuela.dto.datos.DatosMaestro;
import com.andoni.escuela.dto.grupos.GrupoRequest;
import com.andoni.escuela.dto.grupos.GrupoResponse;
import com.andoni.escuela.entities.Aula;
import com.andoni.escuela.entities.Curso;
import com.andoni.escuela.entities.Grupo;
import com.andoni.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

import java.util.List;

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

        DatosAula datosAula = new DatosAula(
                entidad.getAula().getNombre(),
                entidad.getAula().getCapacidad()
        );

        DatosMaestro datosMaestro = new DatosMaestro(
                entidad.getMaestro().getNombre(),
                entidad.getMaestro().getEmail(),
                entidad.getMaestro().getTelefono()
        );

        DatosCurso datosCurso = new DatosCurso(
                entidad.getCurso().getNombre(),
                entidad.getCurso().getDescripcion(),
                entidad.getCurso().getCreditos()
        );

        return new GrupoResponse(
                entidad.getId(),
                datosCurso,
                datosMaestro,
                datosAula,
                entidad.getHorarios().stream()
                        .map(h -> h.getDia() + " " + h.getHoraInicio() + " - " + h.getHoraFin())
                        .toList(),
                entidad.getPeriodo()

        );
    }
}
