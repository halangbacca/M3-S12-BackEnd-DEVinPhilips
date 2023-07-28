package com.medsoft.labmedial.dtos.response;

public record ErrorObject(
        String field,
        String message,
        Object parameter) {

}