package com.medsoft.labmedial.exceptions;

public class ConsultaConflictExeception extends RuntimeException {

  public ConsultaConflictExeception(String msg){
    super(msg);
  }
  public ConsultaConflictExeception(String msg, Throwable cause){
    super(msg, cause);
  }
}
