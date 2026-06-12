package com.andoni.escuela.controllers;

import com.andoni.escuela.dto.horarios.HorarioRequest;
import com.andoni.escuela.dto.horarios.HorarioResponse;
import com.andoni.escuela.services.horarios.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController extends CommonController<HorarioRequest, HorarioResponse, HorarioService>{

    public HorarioController(HorarioService service) {
        super(service);
    }
}
