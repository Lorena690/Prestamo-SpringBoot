package com.ceiba.biblioteca.infrastructure.persistence;

import com.ceiba.biblioteca.domain.Prestamo;
import com.ceiba.biblioteca.domain.repository.PrestamoRepository;
import com.ceiba.biblioteca.infrastructure.persistence.dao.PrestamoDao;
import com.ceiba.biblioteca.infrastructure.persistence.h2.PrestamoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PrestamoRepositoryImpl implements PrestamoRepository {

    @Autowired
    PrestamoJPA prestamoJPA;

    @Override
    public Prestamo createPrestamo(Prestamo prestamo) {
        PrestamoDao prestamoDao = PrestamoDao.builder()
                .isbn(prestamo.getIsbn())
                .identificacionUsuario(prestamo.getIdentificacionUsuario())
                .tipoUsuario(prestamo.getTipoUsuario())
                .fechaMaximaDevolucion(prestamo.getFechaMaximaDevolucion())
                .build();
        return prestamoJPA.save(prestamoDao).toPrestamoDomain();
    }

    @Override
    public Optional<Prestamo> findByIdentificacionUsuario(String identificacionUsuario) {
        Optional<PrestamoDao> prestamoDao  = prestamoJPA.findFirstByIdentificacionUsuario(identificacionUsuario);
        return  prestamoDao.map(PrestamoDao::toPrestamoDomain);
    }

    @Override
    public Optional<Prestamo> findById(Long id) {
        Optional<PrestamoDao> prestamoDao  = prestamoJPA.findById(id);
        return  prestamoDao.map(PrestamoDao::toPrestamoDomain);
    }


}
