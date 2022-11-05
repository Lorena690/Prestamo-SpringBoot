package com.ceiba.biblioteca;

import com.ceiba.biblioteca.calificador.SolicitudPrestarLibroTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class AditionalTest {

    public static final int USUARIO_INVITADO = 3;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void obtenerPrestamoPorIdYElPrestamoNoExiste() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/prestamo/" + 5)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", is("El prestamo con id 5 no existe")));
    }

    @Test
    public void prestamoConIsbnNoPermitidoPorTamanioDeberiaRetornarExcepcion() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("EQWQW8545890", "1111111111", USUARIO_INVITADO))))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.mensaje", is("isbn : El tamaño máximo es de 10 carácteres")));
    }

    @Test
    public void prestamoConIdentificacionUsuarioNoPermitidoPorTamanioDeberiaRetornarExcepcion() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("EQWQW85458", "11111111111111", USUARIO_INVITADO))))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.mensaje", is("identificacionUsuario : El tamaño máximo es de 10 carácteres")));
    }

    @Test
    public void prestamoConIsbnNoPermitidoPorQueNoContieneSoloValoresAlfanumericosDeberiaRetornarExcepcion() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("EQWQW854..", "1111111111", USUARIO_INVITADO))))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.mensaje", is("isbn : Sólo se permiten valores alfanuméricos")));
    }

    @Test
    public void prestamoConIdentificacionUsuarioNoPermitidoPorQueNoContieneSoloValoresAlfanumericosDeberiaRetornarExcepcion() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("EQWQW85458", "11111111..", USUARIO_INVITADO))))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.mensaje", is("identificacionUsuario : Sólo se permiten valores alfanuméricos")));
    }

    @Test
    public void prestamoConIsbnNoPermitidoPorQueEsNullDeberiaRetornarExcepcion() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest(null, "1111111111", USUARIO_INVITADO))))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.mensaje", is("isbn : isbn es obligatorio")));
    }

    @Test
    public void prestamoConIdentificacionUsuarioNoPermitidoPorQueEsNullDeberiaRetornarExcepcion() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SolicitudPrestarLibroTest("EQWQW85458", null, USUARIO_INVITADO))))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.mensaje", is("identificacionUsuario : identificacionUsuario es obligatorio")));
    }
}
