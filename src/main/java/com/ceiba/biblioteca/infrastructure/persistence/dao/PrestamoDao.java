package com.ceiba.biblioteca.infrastructure.persistence.dao;

import com.ceiba.biblioteca.domain.Prestamo;
import lombok.*;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Prestamo")
@NamedQuery(name = "Prestamo.findByIsbn", query = "SELECT prestamo FROM Prestamo prestamo WHERE prestamo.isbn = :isbn")
public class PrestamoDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private String identificacionUsuario;
    @Column(nullable = false)
    private Integer tipoUsuario;

    @Column(nullable = false)
    private LocalDate fechaMaximaDevolucion;

    public Prestamo toPrestamoDomain(){
        return Prestamo.builder()
                .isbn(isbn)
                .identificacionUsuario(identificacionUsuario)
                .tipoUsuario(tipoUsuario)
                .fechaMaximaDevolucion(fechaMaximaDevolucion)
                .id(id)
                .build();

    }
}
