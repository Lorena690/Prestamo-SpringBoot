package com.ceiba.biblioteca.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {

    private Long id;
    private String isbn;
    private String identificacionUsuario;
    private Integer tipoUsuario;
    private LocalDate fechaMaximaDevolucion;

}
