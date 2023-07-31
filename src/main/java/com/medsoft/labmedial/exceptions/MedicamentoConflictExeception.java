package com.medsoft.labmedial.exceptions;

public class MedicamentoConflictExeception extends RuntimeException {

  public MedicamentoConflictExeception(String msg){
    super(msg);
  }
  public MedicamentoConflictExeception(String msg, Throwable cause){
    super(msg, cause);
  }
}
