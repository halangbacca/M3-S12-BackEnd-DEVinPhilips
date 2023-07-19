package com.medsoft.labmedial.exceptions;

public class MedicamentoExeception extends RuntimeException {

  public MedicamentoExeception(String msg){
    super(msg);
  }
  public MedicamentoExeception(String msg, Throwable cause){
    super(msg, cause);
  }
}
