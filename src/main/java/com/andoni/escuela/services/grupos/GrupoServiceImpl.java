package com.andoni.escuela.services.grupos;

import com.andoni.escuela.dto.grupos.GrupoRequest;
import com.andoni.escuela.dto.grupos.GrupoResponse;
import com.andoni.escuela.entities.Aula;
import com.andoni.escuela.entities.Curso;
import com.andoni.escuela.entities.Grupo;
import com.andoni.escuela.entities.Maestro;
import com.andoni.escuela.exceptions.EntidadRelacionadaException;
import com.andoni.escuela.mappers.GrupoMapper;
import com.andoni.escuela.repositories.*;
import com.andoni.escuela.utils.ServiceUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;
    private final HorarioRepository horarioRepository;
    private final InscripcionRespository inscripcionRepository;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;
    private final CursoRepository cursoRepository;

    @Override
    public List<GrupoResponse> listar() {
        log.info("Listado de todos los grupos solicitado");

        return grupoRepository.findAll()
                .stream()
                .map(grupoMapper::entidadAResponse)
                .toList();
    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entidadAResponse(obtenerGrupo(id));
    }

    @Override
    public GrupoResponse registar(GrupoRequest request) {
        log.info("Registrando nuevo grupo...");

        validarDatosUnicos(request);

        Curso curso = ServiceUtils.obtenerEntidadOException(cursoRepository, request.idCurso(), Curso.class);
        Maestro maestro = ServiceUtils.obtenerEntidadOException(maestroRepository, request.idMaestro(), Maestro.class);
        Aula aula = ServiceUtils.obtenerEntidadOException(aulaRepository, request.idAula(), Aula.class);

        Grupo grupo = grupoMapper.requestAEntidad(request, curso, maestro, aula);
        grupoRepository.save(grupo);

        log.info("Nuevo grupo {} registrado", grupo.getPeriodo());
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        log.info("Actualizando grupo...");

        Grupo grupo = obtenerGrupo(id);

        validarCambiosEnDatosUnicos(request,id);

        grupo.actualizar(request.periodo());

        return grupoMapper.entidadAResponse(grupo);

    }

    @Override
    public void eliminar(Long id) {
        Grupo grupo = obtenerGrupo(id);

        log.info("Eliminando grupo {}...", grupo.getPeriodo());

        if (horarioRepository.existsByGrupoId(id)) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el grupo ya que tiene horarios asignados"
            );
        }
        if (inscripcionRepository.existsByGrupoId(id)) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el grupo ya que tiene inscripciones asignadas"
            );
        }

        grupoRepository.delete(grupo);
        log.info("Grupo {} eliminado correctamente", grupo.getPeriodo());
    }

    private Grupo obtenerGrupo(Long id) {
        return ServiceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

    private void validarDatosUnicos(GrupoRequest request) {
        log.info("Validando periodo unico...");
        if (grupoRepository.existsByPeriodo(request.periodo().trim())) {
            throw new IllegalArgumentException(
                    "Ya existe el grupo registrado con el periodo: " + request.periodo()
            );
        }
    }

    private void validarCambiosEnDatosUnicos(GrupoRequest request, Long id) {
        log.info("Validando periodo unico");
        if (grupoRepository.existsByPeriodoAndIdNot(request.periodo().trim(), id)) {
            throw new IllegalArgumentException(
                    "Ya existe el grupo registrado con el periodo: " + request.periodo()
            );
        }
    }
}
