package com.ceiba.biblioteca.aplication.dto.request;

import com.ceiba.biblioteca.domain.Prestamo;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PrestamoRequest {

    @NotBlank(message = "isbn es obligatorio")
    @Size(max = 10, message = "El tamaño máximo es de 10 carácteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Sólo se permiten valores alfanuméricos")
    private String isbn;
    @NotBlank(message = "identificacionUsuario es obligatorio")
    @Size(max = 10, message = "El tamaño máximo es de 10 carácteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Sólo se permiten valores alfanuméricos")
    private String identificacionUsuario;
    @NotNull(message = "tipoUsuario es obligatorio")
    private TipoUsuario tipoUsuario;

    public Prestamo toPrestamoDomain(){
        return Prestamo.builder()
                .isbn(isbn)
                .identificacionUsuario(identificacionUsuario)
                .tipoUsuario(tipoUsuario.getCodigoUsuario())
                .build();
    }

}
