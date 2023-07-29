package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.EmpresaRequest;
import com.medsoft.labmedial.dtos.response.EmpresaResponse;
import com.medsoft.labmedial.exceptions.EmpresaNotFoundException;
import com.medsoft.labmedial.mapper.EmpresaMapper;
import com.medsoft.labmedial.models.Empresa;
import com.medsoft.labmedial.repositories.EmpresaRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class EmpresaServiceTest {
    @Mock
    private EmpresaRepository repository;

    @Mock
    EmpresaMapper mapper;

    @Mock
    OcorrenciaService ocorrenciaService;

    @InjectMocks
    private EmpresaService service;

    private EmpresaRequest request;
    private Empresa empresa;
    private Empresa empresaSalva1;
    private Empresa empresaSalva2;

    private EmpresaResponse empresaResponse;
    private Empresa empresaAtualizadaMapped;

    @BeforeEach
    void setUp() {
        request = new EmpresaRequest(
                "Umbrella Corporation",
                "Our Business is life itself!",
                "Indigo & Pink",
                "www.umbrella.com",
                true
        );

        empresa = Mockito.mock(Empresa.class);
        empresa.setId(1L);
        empresa.setNome("Umbrella Corporation");

        empresaAtualizadaMapped = Mockito.mock(Empresa.class);

        empresaSalva1 = new Empresa(
                1L,
                "Umbrella Corporation",
                "Our Business is life itself!",
                "Indigo & Pink",
                "www.umbrella.com",
                true
        );

        empresaSalva2 = new Empresa(
                1L,
                "Umbrella Corporation",
                "Our Business is life itself!",
                "Indigo & Pink",
                "www.umbrella.com",
                true
        );

        empresaResponse = new EmpresaResponse(
                1L,
                "Umbrella Corporation",
                "Our Business is life itself!",
                "Indigo & Pink",
                "www.umbrella.com",
                true
        );
    }

    @Test
    @DisplayName("Deve retornar a empresa salva")
    void cadastrarEmpresa() {

        Mockito.when(mapper.requestToEmpresa(request))
                .thenReturn(empresaAtualizadaMapped);

        Mockito.when(mapper.empresaToResponse(empresaSalva1))
                .thenReturn(empresaResponse);

        Mockito.when(repository.save(empresaAtualizadaMapped))
                .thenReturn(empresaSalva1);

        EmpresaResponse result = mapper.empresaToResponse(service.cadastrarEmpresa(mapper.requestToEmpresa(request)));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.nome(), request.nome()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(mapper).requestToEmpresa(request);

        Mockito.verify(mapper).empresaToResponse(empresaSalva1);
    }

    @Test
    @DisplayName("Deve atualizar a empresa e retornar a empresa salva")
    void atualizarEmpresa() {
        Mockito.when(repository.existsById(1L))
                .thenReturn(true);

        Mockito.when(mapper.requestToEmpresa(request))
                .thenReturn(empresaAtualizadaMapped);

        Mockito.when(mapper.empresaToResponse(empresaSalva1))
                .thenReturn(empresaResponse);

        Mockito.when(repository.save(empresaAtualizadaMapped))
                .thenReturn(empresaSalva1);

        EmpresaResponse result = mapper.empresaToResponse(service.atualizarEmpresa(1L, mapper.requestToEmpresa(request)));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.nome(), request.nome()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(repository).existsById(1L);

        Mockito.verify(mapper).requestToEmpresa(request);

        Mockito.verify(mapper).empresaToResponse(empresaSalva1);
    }

    @Test
    @DisplayName("Deve lançar o erro de empresa não encontrada")
    void cadastrarEmpresaNaoLocalizado() {

        Exception errorMessage = assertThrows(EmpresaNotFoundException.class,
                () -> service.atualizarEmpresa(1L, mapper.requestToEmpresa(request)));

        assertEquals("Empresa não encontrada!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve excluir uma empresa")
    void excluirEmpresa() {
        Long id = 1L;

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(empresaSalva1));

        service.deletarPorId(id);

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro empresa não localizada quando tentar excluir empresa não cadastrada")
    void excluirEmpresaNaoEncontrada() {
        Exception errorMessage = assertThrows(EmpresaNotFoundException.class,
                () -> service.deletarPorId(1L));

        assertEquals("Empresa não encontrada!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de empresas")
    void listarTodasEmpresas() {
        List<Empresa> empresaList = new ArrayList<>();
        empresaList.add(empresaSalva1);
        empresaList.add(empresaSalva2);

        Mockito.when(repository.findAll())
                .thenReturn(empresaList);

        List<Empresa> result = service.listarEmpresas();

        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar a empresa quando for informado o id da empresa")
    void listarEmpresaPorId() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(empresaSalva1));

        Empresa resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(resultado.getId(), 1L);
    }
}