package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.ExercicioRequest;
import com.medsoft.labmedial.dtos.response.ExercicioResponse;
import com.medsoft.labmedial.dtos.response.NomePaciente;
import com.medsoft.labmedial.enums.TipoExercicio;
import com.medsoft.labmedial.exceptions.ExercicioNotFoundException;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.ExercicioMapper;
import com.medsoft.labmedial.models.Exercicio;
import com.medsoft.labmedial.models.Paciente;
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

  @Mock ExercicioMapper mapper;

  @InjectMocks
  private ExercicioService service;

  private ExercicioRequest request;
  private Paciente paciente;
  private Exercicio exercicioSalvo1;
  private Exercicio exercicioSalvo2;
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

    ExercicioResponse result = service.cadastrarExercicio(request);

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
            () -> service.cadastrarExercicio(request));

    assertEquals("Paciente não encontrado.", errorMessage.getMessage());
  }

  @Test
  @DisplayName("Deve atualizar a exercicio e retornar a exercicio salvo")
  void atualizarExercicio() {
    Mockito.when(repository.findById(1L))
            .thenReturn(Optional.of(exercicioSalvo1));

    Mockito.when(mapper.exercicioRequestToExercicio(request))
            .thenReturn(exercicioAtualizadaMapped);

    Mockito.when(mapper.exercicioToExercicioResponse(exercicioSalvo1))
            .thenReturn(exercicioResponse);

    Mockito.when(repository.save(exercicioAtualizadaMapped))
            .thenReturn(exercicioSalvo1);

    ExercicioResponse result = service.atualizarExercicio(request, 1L);

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
  @DisplayName("Deve lançar o erro de exercicio não encontrado")
  void cadastrarExercicioNaoLocalizada() {
    Mockito.when(repository.findById(1L))
            .thenReturn(Optional.empty());

    Exception errorMessage = assertThrows(ExercicioNotFoundException.class,
            () -> service.atualizarExercicio(request, 1L));

    assertEquals("Exercício não encontrado.", errorMessage.getMessage());
  }

  @Test
  @DisplayName("Deve excluir uma exercicio")
  void excluirExercicio() {
    Long id = 1L;

    Mockito.when(repository.existsById(id))
            .thenReturn(true);

    service.excluirExercicio(id);

    Mockito.verify(repository).existsById(id);
    Mockito.verify(repository).deleteById(id);
  }

  @Test
  @DisplayName("Deve lançar erro exercicio não localizada quando tentar excluir exercicio não cadastrada")
  void excluirExercicioNaoEncontrada() {
    Mockito.when(repository.existsById(1L))
            .thenReturn(false);

    Exception errorMessage = assertThrows(ExercicioNotFoundException.class,
            () -> service.excluirExercicio(1L));

    assertEquals("Exercício não encontrado.", errorMessage.getMessage());
  }


  @Test
  @DisplayName("Deve retornar lista de exercicios quanto não for passado nome do paciente")
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
  @DisplayName("Deve retornar lista de exercicios quanto não for passado nome do paciente")
  void listarExerciciosPorPaciente() {
    List<Optional<Exercicio>> optionalList = new ArrayList<>();
    optionalList.add(Optional.of(exercicioSalvo1));
    optionalList.add(Optional.of(exercicioSalvo2));


    Mockito.when(repository.findAllExerciciosByPacienteNome("Paciente 1"))
            .thenReturn(optionalList);

    List<ExercicioResponse> resultadoComNomePaciente = service.listarExerciciosPorPaciente("Paciente 1");

    assertEquals(resultadoComNomePaciente.size(), 2);
  }
}