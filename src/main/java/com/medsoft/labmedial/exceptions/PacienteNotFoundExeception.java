package com.medsoft.labmedial.exceptions;

public class PacienteNotFoundExeception extends RuntimeException {

  public PacienteNotFoundExeception(String msg){
    super(msg);
  }
  public PacienteNotFoundExeception(String msg, Throwable cause){
    super(msg, cause);
  }
}
