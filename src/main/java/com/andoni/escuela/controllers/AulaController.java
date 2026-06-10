package com.andoni.escuela.controllers;

import com.andoni.escuela.dto.aulas.AulaRequest;
import com.andoni.escuela.dto.aulas.AulaResponse;
import com.andoni.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService>{

    public AulaController(AulaService service) {
        super(service);
    }
}
