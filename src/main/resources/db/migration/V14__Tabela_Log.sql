CREATE TABLE OCORRENCIA (
                            ID NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
                            TABLINK VARCHAR2(100),
                            CODLINK NUMBER,
                            REGATUAL VARCHAR2(4000),
                            REGANTERIOR VARCHAR2(4000),
                            DTAOCORRENCIA DATE,
                            USUARIO VARCHAR(200),
                            TIPO VARCHAR(20)
)