package com.nisum.prueba.respuesta;

import java.io.Serializable;

public class RespError<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T mensageerror;

    public RespError() {
    }

    public T getError() {
        return mensageerror;
    }

    public void setMensagem(T mensageerror) {
        this.mensageerror = mensageerror;
    }
}