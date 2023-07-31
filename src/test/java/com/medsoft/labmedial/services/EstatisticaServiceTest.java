package com.medsoft.labmedial.services;

import com.medsoft.labmedial.models.Estatistica;
import com.medsoft.labmedial.repositories.EstatisticaRepository;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class EstatisticaServiceTest {
    @Mock
    private EstatisticaRepository repository;

    @InjectMocks
    private EstatisticaService service;

    private Estatistica estatisticaSalva1;
    private Estatistica estatisticaSalva2;

    @BeforeEach
    void setUp() {
        estatisticaSalva1 = new Estatistica(
                1L,
                "Tipo",
                "Grupo",
                1L
        );

        estatisticaSalva2 = new Estatistica(
                1L,
                "Tipo",
                "Grupo",
                1L
        );
    }

    @Test
    @DisplayName("Deve retornar lista de estatísticas")
    void listarTodasEstatisticas() {
        List<Estatistica> estatisticaList = new ArrayList<>();
        estatisticaList.add(estatisticaSalva1);
        estatisticaList.add(estatisticaSalva2);

        Mockito.when(repository.findAll())
                .thenReturn(estatisticaList);

        List<Estatistica> result = service.listarEstatistica();

        assertEquals(result.size(), 2);
    }
}