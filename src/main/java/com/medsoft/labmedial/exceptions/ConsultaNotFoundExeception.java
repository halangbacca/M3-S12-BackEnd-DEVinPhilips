package com.medsoft.labmedial.exceptions;

public class ConsultaNotFoundExeception extends RuntimeException {

  public ConsultaNotFoundExeception(String msg){
    super(msg);
  }
  public ConsultaNotFoundExeception(String msg, Throwable cause){
    super(msg, cause);
  }
}
