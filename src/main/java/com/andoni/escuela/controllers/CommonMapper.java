package com.andoni.escuela.controllers;

public interface CommonMapper <RQ,RS,E>{

    E requestAEntidad(RQ request);
    RS entidadAResponse (E request);
}
