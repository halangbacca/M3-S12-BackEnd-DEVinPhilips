package com.medsoft.labmedial.exceptions;

public class MedicamentoNotFoundExeception extends RuntimeException {

  public MedicamentoNotFoundExeception(String msg){
    super(msg);
  }
  public MedicamentoNotFoundExeception(String msg, Throwable cause){
    super(msg, cause);
  }
}
