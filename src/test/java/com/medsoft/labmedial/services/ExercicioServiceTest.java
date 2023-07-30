package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.ExercicioRequest;
import com.medsoft.labmedial.dtos.response.ExercicioResponse;
import com.medsoft.labmedial.dtos.response.NomePaciente;
import com.medsoft.labmedial.enums.NivelUsuario;
import com.medsoft.labmedial.enums.TipoExercicio;
import com.medsoft.labmedial.exceptions.ExercicioNotFoundException;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.ExercicioMapper;
import com.medsoft.labmedial.models.Exercicio;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.repositories.ExercicioRepository;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ExercicioServiceTest {

    @Mock
    private ExercicioRepository repository;

    @Mock
    private PacienteService pacienteService;

    @Mock
    ExercicioMapper mapper;

    @Mock
    UsuarioService usuarioService;

    @Mock
    OcorrenciaService ocorrenciaService;

    @InjectMocks
    private ExercicioService service;

    private ExercicioRequest request;
    private Paciente paciente;
    private Exercicio exercicioSalvo1;
    private Exercicio exercicioSalvo2;
    private Usuario usuario;
    private Exercicio exercicioMapped;
    private ExercicioResponse exercicioResponse;
    private Exercicio exercicioAtualizadaMapped;

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2023-07-27 15:15");
        request = new ExercicioRequest(
                "Exercicio 1",
                date,
                TipoExercicio.FORCA,
                1,
                "Exercício de força",
                1L
        );

        paciente = Mockito.mock(Paciente.class);
        paciente.setId(1L);
        paciente.setNome("Paciente 1");

        exercicioMapped = new Exercicio(
                1L,
                "Exercicio 1",
                date,
                TipoExercicio.FORCA,
                3,
                "Exercício de força",
                paciente,
                null
        );

        exercicioMapped = new Exercicio(
                1L,
                "Exercicio 1",
                date,
                TipoExercicio.FORCA,
                3,
                "Exercicio dash",
                paciente,
                null
        );

        exercicioAtualizadaMapped = Mockito.mock(Exercicio.class);

        exercicioSalvo1 = new Exercicio(
                1L,
                "Exercicio 1",
                date,
                TipoExercicio.FORCA,
                3,
                "Exercicio dash",
                paciente,
                true
        );

        exercicioSalvo2 = new Exercicio(
                1L,
                "Exercicio 1",
                date,
                TipoExercicio.FORCA,
                3,
                "Exercicio dash",
                paciente,
                true
        );

        exercicioResponse = new ExercicioResponse(
                1L,
                "Exercioc 1",
                date,
                "FORÇA",
                3,
                "Exercicio dash",
                new NomePaciente(1L, "Paciente 1"),
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
                true
        );
    }

    @Test
    @DisplayName("Deve retornar a exercicio salvo")
    void cadastrarExercicio() {
        Mockito.when(pacienteService.buscarPorId(request.idPaciente()))
                .thenReturn(paciente);

        Mockito.when(mapper.exercicioRequestToExercicio(request))
                .thenReturn(exercicioMapped);

        Mockito.when(repository.save(exercicioMapped))
                .thenReturn(exercicioSalvo1);

        Mockito.when(mapper.exercicioToExercicioResponse(exercicioSalvo1))
                .thenReturn(exercicioResponse);

        Mockito.when(usuarioService.buscarUsuarioToken("token"))
                .thenReturn(usuario);

        ExercicioResponse result = service.cadastrarExercicio(request, "token");

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.paciente().id(), request.idPaciente()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(pacienteService).buscarPorId(request.idPaciente());

        Mockito.verify(mapper).exercicioRequestToExercicio(request);

        Mockito.verify(mapper).exercicioToExercicioResponse(exercicioSalvo1);
    }

    @Test
    @DisplayName("Deve lançar o erro de paciente não encontrado")
    void cadastrarExercicioPacienteNaoLocalizado() {
        Mockito.when(pacienteService.buscarPorId(request.idPaciente()))
                .thenReturn(null);


        Exception errorMessage = assertThrows(PacienteNotFoundExeception.class,
                () -> service.cadastrarExercicio(request, "1234567890"));

        assertEquals("Paciente não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar a exercício e retornar a exercício salvo")
    void atualizarExercicio() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(exercicioSalvo1));

        Mockito.when(mapper.exercicioRequestToExercicio(request))
                .thenReturn(exercicioAtualizadaMapped);

        Mockito.when(mapper.exercicioToExercicioResponse(exercicioSalvo1))
                .thenReturn(exercicioResponse);

        Mockito.when(repository.save(exercicioAtualizadaMapped))
                .thenReturn(exercicioSalvo1);

        Mockito.when(usuarioService.buscarUsuarioToken("token"))
                .thenReturn(usuario);

        ExercicioResponse result = service.atualizarExercicio(request, 1L, "token");

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.paciente().id(), request.idPaciente()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(repository).findById(1L);

        Mockito.verify(mapper).exercicioRequestToExercicio(request);

        Mockito.verify(mapper).exercicioToExercicioResponse(exercicioSalvo1);
    }

    @Test
    @DisplayName("Deve lançar o erro de exercício não encontrado")
    void cadastrarExercicioNaoLocalizada() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.empty());

        Exception errorMessage = assertThrows(ExercicioNotFoundException.class,
                () -> service.atualizarExercicio(request, 1L, "1234567890"));

        assertEquals("Exercício não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve excluir um exercício")
    void excluirExercicio() {
        Long id = 1L;

        Mockito.when(repository.existsById(id))
                .thenReturn(true);

        Mockito.when(usuarioService.buscarUsuarioToken("token"))
                .thenReturn(usuario);

        service.excluirExercicio(id, "token");

        Mockito.verify(repository).existsById(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro exercício não localizada quando tentar excluir exercício não cadastrado")
    void excluirExercicioNaoEncontrado() {
        Mockito.when(repository.existsById(1L))
                .thenReturn(false);

        Exception errorMessage = assertThrows(ExercicioNotFoundException.class,
                () -> service.excluirExercicio(1L, "1234567890"));

        assertEquals("Exercício não encontrado!", errorMessage.getMessage());
    }


    @Test
    @DisplayName("Deve retornar lista de exercícios quanto não for passado nome do paciente")
    void listarTodasExercicios() {
        List<Exercicio> exercicioList = new ArrayList<>();
        exercicioList.add(exercicioSalvo1);
        exercicioList.add(exercicioSalvo2);

        Mockito.when(repository.findAll())
                .thenReturn(exercicioList);


        List<ExercicioResponse> resultSemNomePaciente = service.listarExerciciosPorPaciente(null);


        assertEquals(resultSemNomePaciente.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar lista de exercícios quanto não for passado nome do paciente")
    void listarExerciciosPorPaciente() {
        List<Optional<Exercicio>> optionalList = new ArrayList<>();
        optionalList.add(Optional.of(exercicioSalvo1));
        optionalList.add(Optional.of(exercicioSalvo2));


        Mockito.when(repository.findAllExerciciosByPacienteNome("Paciente 1"))
                .thenReturn(optionalList);

        List<ExercicioResponse> resultadoComNomePaciente = service.listarExerciciosPorPaciente("Paciente 1");

        assertEquals(resultadoComNomePaciente.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar o exercício ao informar o id de um exercício cadastrado")
    void listarExercicioPorId() {
        Long id = 1L;

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(exercicioSalvo1));

        Exercicio resultado = service.listarExercicioPorId(1L);

        assertEquals(resultado.getId(), 1L);
    }

    @Test
    @DisplayName("Deve lançar erro exercicio não localizada quando tentar listar exercicio não cadastrado")
    void listarExercicioNaoEncontrado() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.empty());

        Exception errorMessage = assertThrows(ExercicioNotFoundException.class,
                () -> service.listarExercicioPorId(1L));

        assertEquals("Exercício não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve retornar uma lista de exercícios ao informar o id de um paciente vinculado")
    void listarExercicioPorIdDoPaciente() {
        List<Optional<Exercicio>> optionalList = new ArrayList<>();
        optionalList.add(Optional.of(exercicioSalvo1));
        optionalList.add(Optional.of(exercicioSalvo2));

        Mockito.when(repository.findAllExerciciosByPacienteId(1L))
                .thenReturn(optionalList);

        List<ExercicioResponse> resultadoComIdPaciente = service.listarExerciciosPorPacienteId(1L);

        assertEquals(resultadoComIdPaciente.size(), 2);
    }
}