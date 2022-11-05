package com.ceiba.biblioteca.aplication.service;

import com.ceiba.biblioteca.aplication.dto.request.PrestamoRequest;
import com.ceiba.biblioteca.aplication.dto.response.GetPrestamoResponse;
import com.ceiba.biblioteca.aplication.dto.response.PrestamoResponse;
import com.ceiba.biblioteca.domain.exception.PrestamoException;
import com.ceiba.biblioteca.domain.exception.PrestamoNotFoundEx;

public interface PrestamoService {
    PrestamoResponse prestamo(PrestamoRequest prestamoRequest) throws PrestamoException;

    GetPrestamoResponse getPrestamo(Long idPrestamo) throws PrestamoNotFoundEx;
}
