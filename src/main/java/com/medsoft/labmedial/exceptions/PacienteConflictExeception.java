package com.medsoft.labmedial.exceptions;

public class PacienteConflictExeception extends RuntimeException {

  public PacienteConflictExeception(String msg){
    super(msg);
  }
  public PacienteConflictExeception(String msg, Throwable cause){
    super(msg, cause);
  }
}
