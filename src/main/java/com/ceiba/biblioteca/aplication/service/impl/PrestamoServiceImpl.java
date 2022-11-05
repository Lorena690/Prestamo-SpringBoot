package com.ceiba.biblioteca.aplication.service.impl;

import com.ceiba.biblioteca.aplication.dto.request.PrestamoRequest;
import com.ceiba.biblioteca.aplication.dto.request.TipoUsuario;
import com.ceiba.biblioteca.aplication.dto.response.GetPrestamoResponse;
import com.ceiba.biblioteca.aplication.dto.response.PrestamoResponse;
import com.ceiba.biblioteca.aplication.service.PrestamoService;
import com.ceiba.biblioteca.domain.Prestamo;
import com.ceiba.biblioteca.domain.exception.PrestamoException;
import com.ceiba.biblioteca.domain.exception.PrestamoNotFoundEx;
import com.ceiba.biblioteca.domain.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import static com.ceiba.biblioteca.domain.constants.PrestamoConstants.MESSAGE_TEMPLATE_ID_NOT_FOUND;
import static com.ceiba.biblioteca.domain.constants.PrestamoConstants.MESSAGE_TEMPLATE_USUARIO_INVITADO;


@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    PrestamoRepository prestamoRepository;

    @Override
    public PrestamoResponse prestamo(PrestamoRequest prestamoRequest) throws PrestamoException {
        if (prestamoRequest.getTipoUsuario().equals(TipoUsuario.USUARIO_INVITADO)) {
            Optional<Prestamo> prestamito = prestamoRepository.findByIdentificacionUsuario(prestamoRequest.getIdentificacionUsuario());
            if (prestamito.isPresent()){
                throw new PrestamoException(String.format(MESSAGE_TEMPLATE_USUARIO_INVITADO, prestamoRequest.getIdentificacionUsuario()));
            }
        }
        Integer days = calculateDays(prestamoRequest);
        LocalDate date = addDaysSkippingWeekends(LocalDate.now(), days);
        Prestamo prestamo = prestamoRequest.toPrestamoDomain();
        prestamo.setFechaMaximaDevolucion(date);
        prestamo = prestamoRepository.createPrestamo(prestamo);
        return PrestamoResponse.builder()
                .id(prestamo.getId())
                .fechaMaximaDevolucion(prestamo.getFechaMaximaDevolucion())
                .build();
    }

    @Override
    public GetPrestamoResponse getPrestamo(Long idPrestamo) throws PrestamoNotFoundEx {
        Optional<Prestamo> prestamito = prestamoRepository.findById(idPrestamo);
        Prestamo prestamo = prestamito.orElseThrow( ()->new PrestamoNotFoundEx(String.format(MESSAGE_TEMPLATE_ID_NOT_FOUND,idPrestamo)));
        return GetPrestamoResponse.builder()
                .id(prestamo.getId())
                .isbn(prestamo.getIsbn())
                .identificacionUsuario(prestamo.getIdentificacionUsuario())
                .tipoUsuario(prestamo.getTipoUsuario())
                .fechaMaximaDevolucion(prestamo.getFechaMaximaDevolucion())
                .build();
    }

    public LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }
        return result;
    }

    public Integer calculateDays(PrestamoRequest prestamoRequest){
        if (prestamoRequest.getTipoUsuario().equals(TipoUsuario.USUARIO_AFILIADO)){
            return 10;
        } else if (prestamoRequest.getTipoUsuario().equals(TipoUsuario.USUARIO_EMPLEADO_BIBLIOTECA)) {
            return 8;
        }else {
            return 7;
        }
    }
}
