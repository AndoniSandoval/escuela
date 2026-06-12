package com.andoni.escuela.controllers;


import com.andoni.escuela.dto.alumnos.AlumnoRequest;
import com.andoni.escuela.dto.alumnos.AlumnoResponse;
import com.andoni.escuela.services.alumnos.AlumnoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController extends CommonController<AlumnoRequest, AlumnoResponse, AlumnoService>{

    public AlumnoController(AlumnoService service) {
        super(service);
    }
}
