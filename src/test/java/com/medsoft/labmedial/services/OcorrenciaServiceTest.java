package com.medsoft.labmedial.services;

import com.medsoft.labmedial.enums.TipoOcorrencia;
import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.repositories.OcorrenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class OcorrenciaServiceTest {

    @Mock
    private OcorrenciaRepository repository;

    @InjectMocks
    private OcorrenciaService service;

    private Ocorrencia ocorrenciaSalva1;
    private Ocorrencia ocorrenciaSalva2;

    @BeforeEach
    void setUp() {
        ocorrenciaSalva1 = new Ocorrencia(
                1L,
                "USUARIO",
                1L,
                "regAtual",
                "regAnterior",
                new Date(),
                null,
                TipoOcorrencia.UPDATE
        );

        ocorrenciaSalva2 = new Ocorrencia(
                1L,
                "USUARIO",
                1L,
                "regAtual",
                "regAnterior",
                new Date(),
                null,
                TipoOcorrencia.UPDATE
        );
    }

    @Test
    @DisplayName("Deve retornar a ocorrência salva")
    void cadastrarOcorrencia() {

        service.cadastrarOcorrencia(ocorrenciaSalva1);

        Mockito.verify(repository, Mockito.times(1))
                .save(ocorrenciaSalva1);
    }

    @Test
    @DisplayName("Deve retornar lista de ocorrências")
    void listarTodasOcorrencias() {
        List<Ocorrencia> ocorrenciaList = new ArrayList<>();
        ocorrenciaList.add(ocorrenciaSalva1);
        ocorrenciaList.add(ocorrenciaSalva2);

        Mockito.when(repository.findAll())
                .thenReturn(ocorrenciaList);

        List<Ocorrencia> result = service.listarOcorrencia();

        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar a ocorrência quando for informado o tabLink e o codLink da ocorrência")
    void listarOcorrencias() {
        List<Ocorrencia> ocorrenciaList = new ArrayList<>();
        ocorrenciaList.add(ocorrenciaSalva1);
        ocorrenciaList.add(ocorrenciaSalva2);

        Mockito.when(repository.findByCodLinkAndTabLink(1L, "USUARIO"))
                .thenReturn(ocorrenciaList);

        List<Ocorrencia> result = service.buscarOcorrencia(1L, "USUARIO");

        assertEquals(result.size(), 2);
    }

}