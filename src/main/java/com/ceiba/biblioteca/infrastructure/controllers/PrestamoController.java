package com.ceiba.biblioteca.infrastructure.controllers;


import com.ceiba.biblioteca.aplication.dto.response.GetPrestamoResponse;
import com.ceiba.biblioteca.aplication.service.PrestamoService;
import com.ceiba.biblioteca.aplication.dto.request.PrestamoRequest;
import com.ceiba.biblioteca.aplication.dto.response.PrestamoResponse;
import com.ceiba.biblioteca.domain.exception.PrestamoException;
import com.ceiba.biblioteca.domain.exception.PrestamoNotFoundEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<PrestamoResponse> prestamo(@Valid @RequestBody PrestamoRequest prestamoRequest) throws PrestamoException {
        PrestamoResponse response = prestamoService.prestamo(prestamoRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id-prestamo}")
    public ResponseEntity<GetPrestamoResponse> getPrestamo(@PathVariable("id-prestamo")Long idPrestamo) throws PrestamoNotFoundEx {
        GetPrestamoResponse getPrestamoResponse = prestamoService.getPrestamo(idPrestamo);
        return ResponseEntity.ok(getPrestamoResponse);
    }
}

