package com.ceiba.biblioteca.aplication.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PrestamoResponse {

    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaMaximaDevolucion;
}
