CREATE TABLE EXAME
(
    ID         NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
    IDPACIENTE NUMBER,
    DESCRICAO  VARCHAR2(200),
    DTAEXAME   DATE,
    TIPO       VARCHAR(200),
    DOCUMENTO  VARCHAR2(300),
    RESULTADO  VARCHAR2(1024),

    FOREIGN KEY (IDPACIENTE) REFERENCES PACIENTE (ID)

)