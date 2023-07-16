package com.medsoft.labmedial.dtos.response;

import org.springframework.validation.FieldError;

public record ExceptionsResponse(String campo, String mensagem) {
    public ExceptionsResponse(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
