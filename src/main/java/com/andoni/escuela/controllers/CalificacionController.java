package com.andoni.escuela.controllers;

import com.andoni.escuela.dto.calificaciones.CalificacionRequest;
import com.andoni.escuela.dto.calificaciones.CalificacionResponse;
import com.andoni.escuela.services.calificaciones.CalificacionService;
import org.springframework.stereotype.Component;

@Component
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService>{

    public CalificacionController(CalificacionService service) {
        super(service);
    }
}
