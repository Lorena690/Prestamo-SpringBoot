package com.ceiba.biblioteca.infrastructure.persistence.h2;

import com.ceiba.biblioteca.infrastructure.persistence.dao.PrestamoDao;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrestamoJPA extends CrudRepository<PrestamoDao,Long> {

    Optional<PrestamoDao> findFirstByIdentificacionUsuario(String identificacionUsuario);

}
