package com.andoni.escuela.mappers;

import com.andoni.escuela.dto.datos.DatosCurso;
import com.andoni.escuela.dto.maestros.MaestroRequest;
import com.andoni.escuela.dto.maestros.MaestroResponse;
import com.andoni.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestro> {

    @Override
    public Maestro requestAEntidad (MaestroRequest request){
        if (request == null) return null;

        return Maestro.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email().trim())
                .telefono(request.telefono().trim())
                .build();

    }

    @Override
    public MaestroResponse entidadAResponse(Maestro entidad){
        if (entidad == null) return null;

        List<DatosCurso> cursos = List.of();

        return new MaestroResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getTelefono(),
                cursos
        );
    }
}
