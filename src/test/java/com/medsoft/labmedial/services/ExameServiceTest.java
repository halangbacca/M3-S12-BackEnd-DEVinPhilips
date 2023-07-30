package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.ConsultaRequest;
import com.medsoft.labmedial.dtos.request.ExameRequest;
import com.medsoft.labmedial.dtos.response.ConsultaResponse;
import com.medsoft.labmedial.dtos.response.ExameResponse;
import com.medsoft.labmedial.dtos.response.NomePaciente;
import com.medsoft.labmedial.exceptions.ConsultaNotFoundExeception;
import com.medsoft.labmedial.exceptions.ExameNotFoundException;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.ExameMapper;
import com.medsoft.labmedial.models.Consulta;
import com.medsoft.labmedial.models.Exame;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.repositories.ExameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ExameServiceTest {
    @Mock
    private ExameRepository repository;

    @Mock
    ExameMapper mapper;

    @Mock
    OcorrenciaService ocorrenciaService;

    @InjectMocks
    private ExameService service;

    private ExameRequest request;
    private Paciente paciente;
    private Exame exameSalvo1;
    private Exame exameSalvo2;
    private ExameResponse exameResponse;
    private Exame exameAtualizadoMapped;

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2023-07-28 15:54");
        request = new ExameRequest(
                "Medicamento 1",
                date,
                "Cápsula",
                "mg",
                "precaucao",
                1L
        );

        paciente = Mockito.mock(Paciente.class);
        paciente.setId(1L);
        paciente.setNome("Paciente 1");

        exameAtualizadoMapped = Mockito.mock(Exame.class);

        exameSalvo1 = new Exame(
                1L,
                "Descrição",
                date,
                "Tipo",
                "Documento",
                "Resultado",
                paciente,
                true
        );

        exameSalvo2 = new Exame(
                1L,
                "Descrição",
                date,
                "Tipo",
                "Documento",
                "Resultado",
                paciente,
                true
        );

        exameResponse = new ExameResponse(
                1L,
                "descricao",
                date,
                "tipo",
                "documento",
                "resultado",
                paciente,
                true
        );
    }

    @Test
    @DisplayName("Deve retornar o exame salvo")
    void cadastrarExame() {

        Mockito.when(mapper.requestToExame(request))
                .thenReturn(exameAtualizadoMapped);

        Mockito.when(mapper.exameToResponse(exameSalvo1))
                .thenReturn(exameResponse);

        Mockito.when(repository.save(exameAtualizadoMapped))
                .thenReturn(exameSalvo1);

        ExameResponse result = mapper.exameToResponse(service.cadastrarExame(mapper.requestToExame(request),"1234567890"));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(mapper).requestToExame(request);

        Mockito.verify(mapper).exameToResponse(exameSalvo1);
    }

    @Test
    @DisplayName("Deve atualizar o exame e retornar o exame salvo")
    void atualizarExame() {
        Mockito.when(repository.existsById(1L))
                .thenReturn(true);

        Mockito.when(mapper.requestToExame(request))
                .thenReturn(exameAtualizadoMapped);

        Mockito.when(mapper.exameToResponse(exameSalvo1))
                .thenReturn(exameResponse);

        Mockito.when(repository.save(exameAtualizadoMapped))
                .thenReturn(exameSalvo1);

        ExameResponse result = mapper.exameToResponse(service.atualizarExame(1L, mapper.requestToExame(request),"1234567890"));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(repository).existsById(1L);

        Mockito.verify(mapper).requestToExame(request);

        Mockito.verify(mapper).exameToResponse(exameSalvo1);
    }

    @Test
    @DisplayName("Deve lançar o erro de exame não encontrado")
    void cadastrarExameNaoLocalizado() {

        Exception errorMessage = assertThrows(ExameNotFoundException.class,
                () -> service.atualizarExame(1L, mapper.requestToExame(request),"1234567890"));

        assertEquals("Exame não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve excluir um exame")
    void excluirExame() {
        Long id = 1L;

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(exameSalvo1));

        service.deletarPorId(id,"1234567890");

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro exame não localizado quando tentar excluir exame não cadastrado")
    void excluirExameNaoEncontrado() {
        Exception errorMessage = assertThrows(ExameNotFoundException.class,
                () -> service.deletarPorId(1L,"1234567890"));

        assertEquals("Exame não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de exames")
    void listarTodosExames() {
        List<Exame> examesList = new ArrayList<>();
        examesList.add(exameSalvo1);
        examesList.add(exameSalvo2);

        Mockito.when(repository.findAll())
                .thenReturn(examesList);

        List<Exame> result = service.listarExames();

        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar o exame quando for informado o id do exame")
    void listarExamePorId() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(exameSalvo1));

        Exame resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(resultado.getId(), 1L);
    }

}