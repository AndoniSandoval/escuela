package com.andoni.escuela.services.cursos;

import com.andoni.escuela.dto.aulas.AulaRequest;
import com.andoni.escuela.dto.cursos.CursoRequest;
import com.andoni.escuela.dto.cursos.CursoResponse;
import com.andoni.escuela.entities.Curso;
import com.andoni.escuela.exceptions.EntidadRelacionadaException;
import com.andoni.escuela.mappers.CursoMapper;
import com.andoni.escuela.repositories.CursoRepository;
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
public class CursoServiceImpl implements CursoService{

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    @Override
    public List<CursoResponse> listar() {
        log.info("Listado de todos los cursos solicitado");

        return cursoRepository.findAll()
                .stream()
                .map(cursoMapper::entidadAResponse)
                .toList();
    }

    @Override
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entidadAResponse(obtenerCurso(id));
    }

    @Override
    public CursoResponse registar(CursoRequest request) {
        validarNombreUnico(request);

        Curso curso = cursoMapper.requestAEntidad(request);
        cursoRepository.save(curso);

        log.info("Nueva curso {} registrado", curso.getNombre());

        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        log.info("Actualizando curso...");

        Curso curso = obtenerCurso(id);

        validarCambiosEnDatosUnicos(request, id);

        curso.actualizar(request.nombre(), request.descripcion(), request.creditos());
        log.info("Actualizando curso con id: {}", id);

        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public void eliminar(Long id) {

        Curso curso = obtenerCurso(id);

        log.info("Eliminando curso {} ...", curso.getNombre());

        if (cursoRepository.existsByGruposId(id)){
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el curo ya que tiene grupos asignados"
            );
        }

        cursoRepository.delete(curso);

        log.info("Curso {} eliminado correctamente", curso.getNombre());
    }

    private Curso obtenerCurso(Long id){
        return ServiceUtils.obtenerEntidadOException(cursoRepository,id, Curso.class);
    }

    private void validarNombreUnico(CursoRequest request) {
        log.info("Validando nombre unico...");
        if (cursoRepository.existsByNombreIgnoreCase(request.nombre().trim())) {
            throw new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
        }
    }

    private void validarCambiosEnDatosUnicos(CursoRequest request, Long id) {
        log.info("Validando nombres unicos...");
        if (cursoRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id)) {
            throw new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
        }
    }
}
