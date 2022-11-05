package com.ceiba.biblioteca.domain.repository;

import com.ceiba.biblioteca.domain.Prestamo;

import java.util.Optional;

public interface PrestamoRepository {

    Prestamo createPrestamo(Prestamo prestamo);

    Optional<Prestamo> findByIdentificacionUsuario(String identificacionUsuario);

    Optional<Prestamo> findById(Long id);
}
