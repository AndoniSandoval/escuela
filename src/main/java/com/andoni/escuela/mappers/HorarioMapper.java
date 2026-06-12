package com.andoni.escuela.mappers;

import com.andoni.escuela.dto.datos.DatosGrupoHorario;
import com.andoni.escuela.dto.horarios.HorarioRequest;
import com.andoni.escuela.dto.horarios.HorarioResponse;
import com.andoni.escuela.entities.Grupo;
import com.andoni.escuela.entities.Horario;
import com.andoni.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

@Component
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario>{

    @Override
    public Horario requestAEntidad(HorarioRequest request) {
        if (request == null) return null;
        return Horario.builder()
                .dia(request.dia().trim())
                .horaInicio(request.horaInicio().trim())
                .horaFin(request.horaFin().trim())
                .build();
    }

    // Sobrecarga metodo
    public Horario requestAEntidad(HorarioRequest request, Grupo grupo) {
        if (request == null) return null;
        return Horario.builder()
                .grupo(grupo)
                .dia(request.dia().trim())
                .horaInicio(request.horaInicio().trim())
                .horaFin(request.horaFin().trim())
                .build();
    }

    @Override
    public HorarioResponse entidadAResponse(Horario entidad) {
        if (entidad == null) return null;

        Grupo grupo = entidad.getGrupo();
        Maestro maestro = grupo.getMaestro();

        DatosGrupoHorario datosGrupo = new DatosGrupoHorario(
                grupo.getCurso().getNombre(),
                String.join(" ", maestro.getNombre(),
                        maestro.getApellidoPaterno(),
                        maestro.getApellidoMaterno()),
                grupo.getAula().getNombre(),
                grupo.getPeriodo()
        );

        return new HorarioResponse(
                entidad.getId(),
                datosGrupo,
                entidad.getDia() + " " + entidad.getHoraInicio() + " - " + entidad.getHoraFin()
        );
    }
}
