ALTER TABLE MEDICAMENTO
    ADD IDPACIENTE NUMBER ADD FOREIGN KEY (IDPACIENTE) REFERENCES PACIENTE (ID);
ALTER TABLE MEDICAMENTO
    ADD SITUACAO NUMBER;

