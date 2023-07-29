package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.DietaRequest;
import com.medsoft.labmedial.dtos.response.DietaResponse;
import com.medsoft.labmedial.dtos.response.NomePaciente;
import com.medsoft.labmedial.enums.TipoDieta;
import com.medsoft.labmedial.exceptions.DietaNotFoundException;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.DietaMapper;
import com.medsoft.labmedial.models.Dieta;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.repositories.DietaRepository;
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
class DietaServiceTest {

    @Mock
    private DietaRepository repository;

    @Mock
    private PacienteService pacienteService;

    @Mock
    DietaMapper mapper;

    @Mock
    OcorrenciaService ocorrenciaService;

    @InjectMocks
    private DietaService service;

    private DietaRequest request;
    private Paciente paciente;
    private Dieta dietaSalva1;
    private Dieta dietaSalva2;
    private Dieta dietaMapped;
    private DietaResponse dietaResponse;
    private Dieta dietaAtualizadaMapped;

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2023-07-27 15:15");
        request = new DietaRequest(
                "Dieta 1",
                date,
                TipoDieta.DASH,
                "Dieta dash",
                1L,
                null
        );

        paciente = Mockito.mock(Paciente.class);
        paciente.setId(1L);
        paciente.setNome("Paciente 1");

        dietaMapped = new Dieta(
                1L,
                "Dieta 1",
                date,
                TipoDieta.DASH,
                "Dieta dash",
                paciente,
                null
        );

        dietaMapped = new Dieta(
                1L,
                "Dieta 1",
                date,
                TipoDieta.DASH,
                "Dieta dash",
                paciente,
                null
        );

        dietaAtualizadaMapped = Mockito.mock(Dieta.class);

        dietaSalva1 = new Dieta(
                1L,
                "Dieta 1",
                date,
                TipoDieta.DASH,
                "Dieta dash",
                paciente,
                true
        );

        dietaSalva2 = new Dieta(
                1L,
                "Dieta 1",
                date,
                TipoDieta.DASH,
                "Dieta dash",
                paciente,
                true
        );

        dietaResponse = new DietaResponse(
                1L,
                "Dieta 1",
                date,
                TipoDieta.DASH,
                "Dieta dash",
                new NomePaciente(1L, "Paciente 1"),
                true
        );
    }

    @Test
    @DisplayName("Deve retornar a dieta salva")
    void cadastrarDieta() {
        Mockito.when(pacienteService.buscarPorId(request.idPaciente()))
                .thenReturn(paciente);

        Mockito.when(mapper.dietaRequestToDieta(request))
                .thenReturn(dietaMapped);

        Mockito.when(repository.save(dietaMapped))
                .thenReturn(dietaSalva1);

        Mockito.when(mapper.dietaToDietaResponse(dietaSalva1))
                .thenReturn(dietaResponse);

        DietaResponse result = service.cadastrarDieta(request);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.paciente().id(), request.idPaciente()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(pacienteService).buscarPorId(request.idPaciente());

        Mockito.verify(mapper).dietaRequestToDieta(request);

        Mockito.verify(mapper).dietaToDietaResponse(dietaSalva1);
    }

    @Test
    @DisplayName("Deve lançar o erro de paciente não encontrado")
    void cadastrarDietaPacienteNaoLocalizado() {
        Mockito.when(pacienteService.buscarPorId(request.idPaciente()))
                .thenReturn(null);


        Exception errorMessage = assertThrows(PacienteNotFoundExeception.class,
                () -> service.cadastrarDieta(request));

        assertEquals("Paciente não encontrado.", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar a dieta e retornar a dieta salva")
    void atualizarDieta() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(dietaSalva1));

        Mockito.when(mapper.dietaRequestToDieta(request))
                .thenReturn(dietaAtualizadaMapped);

        Mockito.when(mapper.dietaToDietaResponse(dietaSalva1))
                .thenReturn(dietaResponse);

        Mockito.when(repository.save(dietaAtualizadaMapped))
                .thenReturn(dietaSalva1);

        DietaResponse result = service.atualizarDieta(request, 1L);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.paciente().id(), request.idPaciente()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(repository).findById(1L);

        Mockito.verify(mapper).dietaRequestToDieta(request);

        Mockito.verify(mapper).dietaToDietaResponse(dietaSalva1);
    }

    @Test
    @DisplayName("Deve lançar o erro de dieta não encontrado")
    void cadastrarDietaNaoLocalizada() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.empty());

        Exception errorMessage = assertThrows(DietaNotFoundException.class,
                () -> service.atualizarDieta(request, 1L));

        assertEquals("Dieta não encontrada.", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve excluir uma dieta")
    void excluirDieta() {
        Long id = 1L;

        Mockito.when(repository.existsById(id))
                .thenReturn(true);

        service.excluirDieta(id);

        Mockito.verify(repository).existsById(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro dieta não localizada quando tentar excluir dieta não cadastrada")
    void excluirDietaNaoEncontrada() {
        Mockito.when(repository.existsById(1L))
                .thenReturn(false);

        Exception errorMessage = assertThrows(DietaNotFoundException.class,
                () -> service.excluirDieta(1L));

        assertEquals("Dieta não encontrada.", errorMessage.getMessage());
    }


    @Test
    @DisplayName("Deve retornar lista de dietas quanto não for passado nome do paciente")
    void listarTodasDietas() {
        List<Dieta> dietaList = new ArrayList<>();
        dietaList.add(dietaSalva1);
        dietaList.add(dietaSalva2);

        Mockito.when(repository.findAll())
                .thenReturn(dietaList);


        List<DietaResponse> resultSemNomePaciente = service.listarDietasPorPaciente(null);


        assertEquals(resultSemNomePaciente.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar lista de dietas quanto não for passado nome do paciente")
    void listarDietasPorPaciente() {
        List<Optional<Dieta>> optionalList = new ArrayList<>();
        optionalList.add(Optional.of(dietaSalva1));
        optionalList.add(Optional.of(dietaSalva2));


        Mockito.when(repository.findAllDietasByPacienteNome("Paciente 1"))
                .thenReturn(optionalList);

        List<DietaResponse> resultadoComNomePaciente = service.listarDietasPorPaciente("Paciente 1");

        assertEquals(resultadoComNomePaciente.size(), 2);
    }
}