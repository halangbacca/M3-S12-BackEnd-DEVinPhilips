package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.ConsultaRequest;
import com.medsoft.labmedial.dtos.response.ConsultaResponse;
import com.medsoft.labmedial.dtos.response.NomePaciente;
import com.medsoft.labmedial.enums.NivelUsuario;
import com.medsoft.labmedial.exceptions.ConsultaNotFoundExeception;
import com.medsoft.labmedial.mapper.ConsultaMapper;
import com.medsoft.labmedial.models.Consulta;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.repositories.ConsultaRepository;
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
class ConsultaServiceTest {
    @Mock
    private ConsultaRepository repository;

    @Mock
    ConsultaMapper mapper;

    @Mock
    UsuarioService usuarioService;

    @Mock
    OcorrenciaService ocorrenciaService;

    @InjectMocks
    private ConsultaService service;

    private ConsultaRequest request;
    private Paciente paciente;
    private Consulta consultaSalva1;
    private Consulta consultaSalva2;
    private Usuario usuario;
    private ConsultaResponse consultaResponse;
    private Consulta consultaAtualizadaMapped;

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2023-07-28 15:54");
        request = new ConsultaRequest(
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

        consultaAtualizadaMapped = Mockito.mock(Consulta.class);

        consultaSalva1 = new Consulta(
                1L,
                "Motivo",
                date,
                "Problema",
                "Medicação",
                "Precaucao",
                paciente,
                true
        );

        consultaSalva2 = new Consulta(
                1L,
                "Motivo",
                date,
                "Problema",
                "Medicação",
                "Precaucao",
                paciente,
                true
        );

        consultaResponse = new ConsultaResponse(
                1L,
                new NomePaciente(1L, "Paciente 1"),
                "Motivo",
                date,
                "Problema",
                "Medicação",
                "Precaução",
                true
        );

        usuario = new Usuario(
                1L,
                "Usuário",
                "Masculino",
                "956.484.960-87",
                "(11)11111-1111",
                "teste@outlook.com",
                "senha",
                NivelUsuario.ADMINISTRADOR,
                true);
    }

    @Test
    @DisplayName("Deve retornar a consulta salva")
    void cadastrarConsulta() {

        Mockito.when(mapper.requestToConsulta(request))
                .thenReturn(consultaAtualizadaMapped);

        Mockito.when(mapper.consultaToResponse(consultaSalva1))
                .thenReturn(consultaResponse);

        Mockito.when(repository.save(consultaAtualizadaMapped))
                .thenReturn(consultaSalva1);

        Mockito.when(usuarioService.buscarUsuarioToken("token"))
                .thenReturn(usuario);

        ConsultaResponse result = mapper.consultaToResponse(service.cadastrarConsulta(mapper.requestToConsulta(request), "token"));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.paciente().id(), request.idPaciente()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(mapper).requestToConsulta(request);

        Mockito.verify(mapper).consultaToResponse(consultaSalva1);
    }

    @Test
    @DisplayName("Deve atualizar a consulta e retornar a consulta salva")
    void atualizarConsulta() {
        Mockito.when(repository.existsById(1L))
                .thenReturn(true);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(consultaSalva1));

        Mockito.when(mapper.requestToConsulta(request))
                .thenReturn(consultaAtualizadaMapped);

        Mockito.when(mapper.consultaToResponse(consultaSalva1))
                .thenReturn(consultaResponse);

        Mockito.when(repository.save(consultaAtualizadaMapped))
                .thenReturn(consultaSalva1);

        Mockito.when(usuarioService.buscarUsuarioToken("token"))
                .thenReturn(usuario);

        ConsultaResponse result = mapper.consultaToResponse(service.atualizarConsulta(1L, mapper.requestToConsulta(request), "token"));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.paciente().id(), request.idPaciente()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(repository).existsById(1L);

        Mockito.verify(mapper).requestToConsulta(request);

        Mockito.verify(mapper).consultaToResponse(consultaSalva1);
    }

    @Test
    @DisplayName("Deve lançar o erro de consulta não encontrada")
    void cadastrarConsultaNaoLocalizada() {

        Exception errorMessage = assertThrows(ConsultaNotFoundExeception.class,
                () -> service.atualizarConsulta(1L, mapper.requestToConsulta(request), "123456789"));

        assertEquals("Consulta não encontrada!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve excluir uma consulta")
    void excluirConsulta() {
        Long id = 1L;

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(consultaSalva1));

        Mockito.when(usuarioService.buscarUsuarioToken("token"))
                .thenReturn(usuario);

        service.deletarPorId(id, "token");

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro consulta não localizada quando tentar excluir consulta não cadastrada")
    void excluirConsultaNaoEncontrada() {
        Exception errorMessage = assertThrows(ConsultaNotFoundExeception.class,
                () -> service.deletarPorId(1L, "123456789"));

        assertEquals("Consulta não encontrada!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de consultas quando não for informado o nome do paciente")
    void listarTodasConsultas() {
        List<Consulta> consultasList = new ArrayList<>();
        consultasList.add(consultaSalva1);
        consultasList.add(consultaSalva2);

        Mockito.when(repository.findAll())
                .thenReturn(consultasList);

        List<Consulta> result = service.listarConsultas(null);

        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar a consulta quando for informado o id da consulta")
    void listarConsultaPorId() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(consultaSalva1));

        Consulta resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(resultado.getId(), 1L);
    }

    @Test
    @DisplayName("Deve retornar uma lista de consultas quando for informado o id do paciente")
    void listarConsultaPorPacienteId() {
        List<Optional<Consulta>> optionalList = new ArrayList<>();
        optionalList.add(Optional.of(consultaSalva1));
        optionalList.add(Optional.of(consultaSalva2));

        Mockito.when(repository.findAllConsultasByPacienteId(1L))
                .thenReturn(optionalList);

        List<ConsultaResponse> resultadoComIdPaciente = service.listarConsultasPorPacienteId(1L);

        assertEquals(resultadoComIdPaciente.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar uma lista de consultas quando for informado o nome do paciente")
    void listarConsultaPorNomePaciente() {
        List<Consulta> optionalList = new ArrayList<>();
        optionalList.add(consultaSalva1);
        optionalList.add(consultaSalva2);

        Mockito.when(repository.findAllConsultasByPacienteNome("Halan"))
                .thenReturn(optionalList);

        List<Consulta> resultadoComIdPaciente = service.listarConsultas("Halan");

        assertEquals(resultadoComIdPaciente.size(), 2);
    }

}