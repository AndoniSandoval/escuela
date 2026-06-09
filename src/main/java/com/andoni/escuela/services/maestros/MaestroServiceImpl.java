package com.andoni.escuela.services.maestros;

import com.andoni.escuela.dto.maestros.MaestroRequest;
import com.andoni.escuela.dto.maestros.MaestroResponse;
import com.andoni.escuela.entities.Maestro;
import com.andoni.escuela.exceptions.EntidadRelacionadaException;
import com.andoni.escuela.mappers.MaestroMapper;
import com.andoni.escuela.repositories.GrupoRepository;
import com.andoni.escuela.repositories.MaestroRepository;
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
public class MaestroServiceImpl implements MaestroService {

    private final MaestroRepository maestroRepository;
    private final GrupoRepository grupoRepository;
    private final MaestroMapper maestroMapper;

    @Override
    public List<MaestroResponse> listar() {
        log.info("Listado de todos los maestros solicitado: ");
        return maestroRepository.findAll().stream().map(maestroMapper::entidadAResponse).toList();
    }

    @Override
    public MaestroResponse obtenerPorId(Long id) {
        return maestroMapper.entidadAResponse(obtenerMaestro(id));
    }

    @Override
    public MaestroResponse registar(MaestroRequest request) {
        log.info("Registrando nuevo maestro...");

        validarDatosUnicos(request);

        Maestro maestro = maestroMapper.requestAEntidad(request);
        maestroRepository.save(maestro);

        log.info("Nuevo maestro {} registrado", maestro.getNombre());
        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {
        log.info("Actualizando maestro...");

        Maestro maestro = obtenerMaestro(id);

        validarCambiosEnDatosUnicos(request, id);
        maestro.actualizar(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(), request.email(), request.telefono());
        log.info("Maestro {} actualizado correctamente ", maestro.getNombre());
        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public void eliminar(Long id) {
        Maestro maestro = obtenerMaestro(id);
        log.info("Eliminando maestro {}...", maestro.getNombre());
        if (grupoRepository.existsByMaestroId(id)) {
            throw new EntidadRelacionadaException("No se puede eliminar al maestro ya que tiene grupos asignados");
        }
        maestroRepository.delete(maestro);
        log.info("Maestro {} eliminado correctamente", maestro.getNombre());
    }

    private Maestro obtenerMaestro(Long id) {
        return ServiceUtils.obtenerEntidadOException(maestroRepository, id, Maestro.class);
    }

    private void validarDatosUnicos(MaestroRequest request) {
        log.info("Validando email unico...");
        if (maestroRepository.existsByEmailIgnoreCase(request.email().trim())) {
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: " + request.email());
        }

        log.info("Validando telefono unico...");
        if (maestroRepository.existsByTelefono(request.telefono())) {
            throw new IllegalArgumentException("Ya existe un maestro registrado con el telefono: " + request.telefono());
        }
    }

    private void validarCambiosEnDatosUnicos(MaestroRequest request, Long id) {
        log.info("Validando email unico...");
        if (maestroRepository.existsByEmailIgnoreCaseAndIdNot(request.email().trim(), id)) {
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: " + request.email());
        }

        log.info("Validando telefono unico...");
        if (maestroRepository.existsByTelefonoAndIdNot(request.telefono(), id)) {
            throw new IllegalArgumentException("Ya existe un maestro registrado con el telefono: " + request.telefono());
        }
    }
}
