package com.medsoft.labmedial.exceptions;

public class PacienteExeception extends RuntimeException {

  public PacienteExeception(String msg){
    super(msg);
  }
  public PacienteExeception(String msg, Throwable cause){
    super(msg, cause);
  }
}
