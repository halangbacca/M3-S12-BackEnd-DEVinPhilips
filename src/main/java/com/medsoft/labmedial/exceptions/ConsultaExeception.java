package com.medsoft.labmedial.exceptions;

public class ConsultaExeception extends RuntimeException {

  public ConsultaExeception(String msg){
    super(msg);
  }
  public ConsultaExeception(String msg, Throwable cause){
    super(msg, cause);
  }
}
