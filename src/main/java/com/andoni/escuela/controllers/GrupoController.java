package com.andoni.escuela.controllers;

import com.andoni.escuela.dto.grupos.GrupoRequest;
import com.andoni.escuela.dto.grupos.GrupoResponse;
import com.andoni.escuela.services.grupos.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService>{

    public GrupoController(GrupoService service){
        super(service);
    }
}
