package com.ceiba.biblioteca.aplication.dto.request;

import com.ceiba.biblioteca.domain.exception.PrestamoException;
import com.fasterxml.jackson.annotation.JsonCreator;

import static com.ceiba.biblioteca.domain.constants.PrestamoConstants.ERROR_TIPO_USUARIO;


public enum TipoUsuario {
    USUARIO_AFILIADO(1,"Usuario afiliado"),
    USUARIO_EMPLEADO_BIBLIOTECA(2, "Usuario empleado biblioteca"),
    USUARIO_INVITADO(3, "Usuario invitado");

    private final Integer codigoUsuario;
    private final String nombreTipoUsuario;

    TipoUsuario(Integer codigoUsuario, String nombreTipoUsuario) {
        this.codigoUsuario = codigoUsuario;
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

    public Integer getCodigoUsuario() {
        return codigoUsuario;
    }

    public String getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TipoUsuario fromInteger(Integer codigoUsuario) throws PrestamoException {
        for(TipoUsuario t : TipoUsuario.values()){
            if(t.getCodigoUsuario().equals(codigoUsuario)){
                return t;
            }
        }
        throw new PrestamoException(ERROR_TIPO_USUARIO);
    }


    @Override
    public String toString() {
        return codigoUsuario.toString();
    }
}
