package com.andoni.escuela.services.aulas;

import com.andoni.escuela.dto.aulas.AulaRequest;
import com.andoni.escuela.dto.aulas.AulaResponse;
import com.andoni.escuela.entities.Aula;
import com.andoni.escuela.exceptions.EntidadRelacionadaException;
import com.andoni.escuela.mappers.AulaMapper;
import com.andoni.escuela.repositories.AulaRepository;
import com.andoni.escuela.repositories.GrupoRepository;
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
public class AulaServiceImpl implements AulaService{

    private final AulaRepository aulaRepository;
    private final GrupoRepository grupoRepository;
    private final AulaMapper aulaMapper;

    @Override
    public List<AulaResponse> listar(){
        log.info("Listado de todos los aulas solicitado");

        return aulaRepository.findAll()
                .stream()
                .map(aulaMapper::entidadAResponse)
                .toList();
    }

    @Override
    public AulaResponse obtenerPorId(Long id){

        return aulaMapper.entidadAResponse(obtenerAula(id));
    }

    @Override
    public AulaResponse registar(AulaRequest request) {
        validarNombreUnico(request);

        Aula aula = aulaMapper.requestAEntidad(request);
        aulaRepository.save(aula);

        log.info("Nueva aula {} registrada", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        log.info("Actualizando aula...");
        Aula aula = obtenerAula(id);

        validarCambiosEnDatosUnicos(request, id);

        aula.actualizar(request.nombre(), request.capacidad());
        log.info("Actualizando aula con id: {}", id);

        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(Long id) {

        Aula aula = obtenerAula(id);

        log.info("Eliminando aula {}...", aula.getNombre());

        if (grupoRepository.existsByAulaId(id)) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el aula ya que tiene grupos asignados"
            );
        }

        aulaRepository.delete(aula);

        log.info("Aula {} eliminado correctamente", aula.getNombre());
    }

    private Aula obtenerAula(Long id) {
        return ServiceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }

    private void validarNombreUnico(AulaRequest request) {
        log.info("Validando nombre unico...");
        if (aulaRepository.existsByNombreIgnoreCase(request.nombre().trim())) {
            throw new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
        }
    }

    private void validarCambiosEnDatosUnicos(AulaRequest request, Long id) {
        log.info("Validando nombre unico...");
        if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id)) {
            throw new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
        }
    }

}
