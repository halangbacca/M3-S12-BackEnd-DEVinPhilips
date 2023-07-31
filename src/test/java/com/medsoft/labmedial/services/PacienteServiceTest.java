package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.PacienteRequest;
import com.medsoft.labmedial.dtos.response.PacienteResponse;
import com.medsoft.labmedial.enums.EstadoCivil;
import com.medsoft.labmedial.enums.NivelUsuario;
import com.medsoft.labmedial.exceptions.PacienteConflictExeception;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.PacienteMapper;
import com.medsoft.labmedial.models.*;
import com.medsoft.labmedial.repositories.PacienteRepository;
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
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class PacienteServiceTest {
    @Mock
    private PacienteRepository repository;

    @Mock
    PacienteMapper mapper;

    @Mock
    OcorrenciaService ocorrenciaService;

    @Mock
    UsuarioService usuarioService;

    @InjectMocks
    private PacienteService service;

    private PacienteRequest request;
    private Paciente paciente;
    private Paciente pacienteSalvo1;
    private Paciente pacienteSalvo2;
    private Usuario usuario;
    private PacienteResponse pacienteResponse;
    private Paciente pacienteAtualizadoMapped;

    @BeforeEach
    void setUp() throws ParseException {
        LocalDate date = LocalDate.now();

        paciente = Mockito.mock(Paciente.class);
        paciente.setId(1L);
        paciente.setNome("Paciente 1");

        List<Alergia> listaAlergias = new ArrayList<>();
        List<Precaucao> listaPrecaucoes = new ArrayList<>();

        listaAlergias.add(new Alergia(1L, "Teste", paciente));
        listaPrecaucoes.add(new Precaucao(1L, "Teste", paciente));

        request = new PacienteRequest(
                "Paciente 1",
                "Masculino",
                date,
                "560.750.800-58",
                "40.616.562-2",
                EstadoCivil.SOLTEIRO,
                "(11)11111-1111",
                "halan@outlook.com",
                "Itajaiense",
                "(11)11111-1111",
                "SUS",
                "111111",
                date,
                "88309-000",
                "Itajaí",
                "SC",
                "Rua São Joaquim",
                "137",
                "Casa",
                "São Vicente",
                "Teste",
                listaAlergias,
                listaPrecaucoes
        );

        pacienteAtualizadoMapped = Mockito.mock(Paciente.class);

        pacienteSalvo1 = new Paciente(
                1L,
                "Paciente 1",
                "Masculino",
                date,
                "560.750.800-58",
                "40.616.562-2",
                EstadoCivil.SOLTEIRO,
                "(11)11111-1111",
                "halan@outlook.com",
                "Itajaiense",
                "(11)11111-1111",
                "SUS",
                "111111",
                date,
                new Endereco(
                        1L,
                        paciente,
                        "88309-000",
                        "Itajaí",
                        "SC",
                        "Rua São Joaquim",
                        "137",
                        "Casa",
                        "São Vicente",
                        "Teste"),
                listaAlergias,
                listaPrecaucoes
        );

        pacienteSalvo2 = new Paciente(
                1L,
                "Paciente 1",
                "Masculino",
                date,
                "560.750.800-58",
                "40.616.562-2",
                EstadoCivil.SOLTEIRO,
                "(11)11111-1111",
                "halan@outlook.com",
                "Itajaiense",
                "(11)11111-1111",
                "SUS",
                "111111",
                date,
                new Endereco(
                        1L,
                        paciente,
                        "88309-000",
                        "Itajaí",
                        "SC",
                        "Rua São Joaquim",
                        "137",
                        "Casa",
                        "São Vicente",
                        "Teste"),
                listaAlergias,
                listaPrecaucoes
        );

        pacienteResponse = new PacienteResponse(
                1L,
                "Paciente 1",
                "Masculino",
                date,
                "560.750.800-58",
                "40.616.562-2",
                EstadoCivil.SOLTEIRO,
                "(11)11111-1111",
                "halan@outlook.com",
                "Itajaiense",
                "(11)11111-1111",
                "SUS",
                "111111",
                date,
                "88309-000",
                "Itajaí",
                "SC",
                "Rua São Joaquim",
                "137",
                "Casa",
                "São Vicente",
                "Teste",
                listaAlergias,
                listaPrecaucoes
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
                true
        );
    }

    @Test
    @DisplayName("Deve retornar o paciente salvo")
    void cadastrarPaciente() {

        Mockito.when(mapper.requestToPaciente(request))
                .thenReturn(pacienteAtualizadoMapped);

        Mockito.when(mapper.pacienteToResponse(pacienteSalvo1))
                .thenReturn(pacienteResponse);

        Mockito.when(repository.save(pacienteAtualizadoMapped))
                .thenReturn(pacienteSalvo1);

        Mockito.when(usuarioService.buscarUsuarioToken("token"))
                .thenReturn(usuario);

        PacienteResponse result = mapper.pacienteToResponse(service.cadastrarPaciente(mapper.requestToPaciente(request), "token"));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.nome(), request.nome())
        );

        Mockito.verify(mapper).requestToPaciente(request);

        Mockito.verify(mapper).pacienteToResponse(pacienteSalvo1);
    }

    @Test
    @DisplayName("Deve retornar PacienteConflictExeception ao cadastrar paciente com CPF já cadastrado")
    void cadastrarPacienteCpfJaCadastrado() {

        Mockito.when(repository.findByCpf(request.cpf())).thenReturn(Optional.ofNullable(pacienteSalvo1));

        Exception errorMessage = assertThrows(PacienteConflictExeception.class,
                () -> service.cadastrarPaciente(pacienteSalvo1, "token"));

        assertEquals("CPF já cadastrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve retornar PacienteConflictExeception ao cadastrar paciente com e-mail já cadastrado")
    void cadastrarPacienteEmailJaCadastrado() {

        Mockito.when(repository.findByEmail(request.email())).thenReturn(Optional.ofNullable(pacienteSalvo1));

        Exception errorMessage = assertThrows(PacienteConflictExeception.class,
                () -> service.cadastrarPaciente(pacienteSalvo1, "token"));

        assertEquals("E-mail já cadastrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar o paciente e retornar o paciente salvo")
    void atualizarPaciente() {
        Mockito.when(repository.existsById(1L))
                .thenReturn(true);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(pacienteSalvo1));

        Mockito.when(mapper.requestToPaciente(request))
                .thenReturn(pacienteAtualizadoMapped);

        Mockito.when(mapper.pacienteToResponse(pacienteSalvo1))
                .thenReturn(pacienteResponse);

        Mockito.when(repository.save(pacienteAtualizadoMapped))
                .thenReturn(pacienteSalvo1);

        Mockito.when(usuarioService.buscarUsuarioToken("token"))
                .thenReturn(usuario);

        PacienteResponse result = mapper.pacienteToResponse(service.atualizarPaciente(1L, mapper.requestToPaciente(request), "token"));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.nome(), request.nome())
        );

        Mockito.verify(repository).existsById(1L);

        Mockito.verify(mapper).requestToPaciente(request);

        Mockito.verify(mapper).pacienteToResponse(pacienteSalvo1);
    }

    @Test
    @DisplayName("Deve lançar o erro de paciente não encontrado")
    void atualizarPacienteNaoLocalizado() {

        Exception errorMessage = assertThrows(PacienteNotFoundExeception.class,
                () -> service.atualizarPaciente(1L, mapper.requestToPaciente(request), "token"));

        assertEquals("Paciente não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve excluir um paciente")
    void excluirPaciente() {
        Long id = 1L;

        Mockito.when(usuarioService.buscarUsuarioToken("token"))
                .thenReturn(usuario);

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(pacienteSalvo1));

        service.deletarPorId(id, "token");

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro paciente não localizado quando tentar excluir paciente não cadastrado")
    void excluirPacienteNaoEncontrado() {
        Exception errorMessage = assertThrows(PacienteNotFoundExeception.class,
                () -> service.deletarPorId(1L, "token"));

        assertEquals("Paciente não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de pacientes")
    void listarTodosPacientes() {
        List<Paciente> pacientesList = new ArrayList<>();
        pacientesList.add(pacienteSalvo1);
        pacientesList.add(pacienteSalvo2);

        Mockito.when(repository.findAll())
                .thenReturn(pacientesList);

        List<Paciente> result = service.listarPacientes();

        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar o paciente quando for informado o id do paciente")
    void listarPacientePorId() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(pacienteSalvo1));

        Paciente resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(resultado.getId(), 1L);
    }

    @Test
    @DisplayName("Deve lançar erro paciente não encontrado ao informar o id de um paciente não cadastrado")
    void listarPacientePorIdNaoEncontrado() {
        Exception errorMessage = assertThrows(PacienteNotFoundExeception.class,
                () -> service.buscarPorId(1L));

        assertEquals("Paciente não encontrado!", errorMessage.getMessage());
    }

}