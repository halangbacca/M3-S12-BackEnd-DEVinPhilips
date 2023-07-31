package com.medsoft.labmedial.exceptions;

public class EmpresaNotFoundException extends RuntimeException {

    public EmpresaNotFoundException(String msg) {
        super(msg);
    }

    public EmpresaNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
