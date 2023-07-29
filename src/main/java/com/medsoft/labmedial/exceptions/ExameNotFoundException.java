package com.medsoft.labmedial.exceptions;

public class ExameNotFoundException extends RuntimeException {
    public ExameNotFoundException(String msg) {
        super(msg);
    }

    public ExameNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
