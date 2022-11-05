package com.ceiba.biblioteca.aplication.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetPrestamoResponse {

    private Long id;
    private String isbn;
    private String identificacionUsuario;
    private Integer tipoUsuario;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaMaximaDevolucion;
}

