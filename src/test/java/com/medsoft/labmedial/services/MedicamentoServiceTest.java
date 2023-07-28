package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.MedicamentoRequest;
import com.medsoft.labmedial.dtos.response.MedicamentoResponse;
import com.medsoft.labmedial.dtos.response.NomePaciente;
import com.medsoft.labmedial.exceptions.MedicamentoNotFoundExeception;
import com.medsoft.labmedial.mapper.MedicamentoMapper;
import com.medsoft.labmedial.models.Medicamento;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.repositories.MedicamentoRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class MedicamentoServiceTest {
    @Mock
    private MedicamentoRepository repository;

    @Mock
    MedicamentoMapper mapper;

    @InjectMocks
    private MedicamentoService service;

    private MedicamentoRequest request;
    private Paciente paciente;
    private Medicamento medicamentoSalvo1;
    private Medicamento medicamentoSalvo2;
    private MedicamentoResponse medicamentoResponse;
    private Medicamento medicamentoAtualizadoMapped;

    @BeforeEach
    void setUp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2023-07-28 15:54");
        request = new MedicamentoRequest(
                "Medicamento 1",
                date,
                "Cápsula",
                "mg",
                1L,
                "Teste",
                1L
        );

        paciente = Mockito.mock(Paciente.class);
        paciente.setId(1L);
        paciente.setNome("Paciente 1");

        medicamentoAtualizadoMapped = Mockito.mock(Medicamento.class);

        medicamentoSalvo1 = new Medicamento(
                1L,
                "Medicamento 1",
                date,
                "Cápsula",
                "mg",
                1L,
                "Teste",
                paciente,
                true
        );

        medicamentoSalvo2 = new Medicamento(
                1L,
                "Medicamento 1",
                date,
                "Cápsula",
                "mg",
                1L,
                "Teste",
                paciente,
                true
        );

        medicamentoResponse = new MedicamentoResponse(
                1L,
                new NomePaciente(1L, "Paciente 1"),
                "Medicamento 1",
                date,
                "Cápsula",
                "mg",
                1L,
                "Teste",
                true
        );
    }

    @Test
    @DisplayName("Deve retornar o medicamento salvo")
    void cadastrarMedicamento() {

        Mockito.when(mapper.requestToMedicamento(request))
                .thenReturn(medicamentoAtualizadoMapped);

        Mockito.when(mapper.medicamentoToMedicamentoResponse(medicamentoSalvo1))
                .thenReturn(medicamentoResponse);

        Mockito.when(repository.save(medicamentoAtualizadoMapped))
                .thenReturn(medicamentoSalvo1);

        MedicamentoResponse result = mapper.medicamentoToMedicamentoResponse(service.cadastrarMedicamento(mapper.requestToMedicamento(request)));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.paciente().id(), request.idPaciente()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(mapper).requestToMedicamento(request);

        Mockito.verify(mapper).medicamentoToMedicamentoResponse(medicamentoSalvo1);
    }

    @Test
    @DisplayName("Deve atualizar o medicamento e retornar o medicamento salvo")
    void atualizarMedicamento() {
        Mockito.when(repository.existsById(1L))
                .thenReturn(true);

        Mockito.when(mapper.requestToMedicamento(request))
                .thenReturn(medicamentoAtualizadoMapped);

        Mockito.when(mapper.medicamentoToMedicamentoResponse(medicamentoSalvo1))
                .thenReturn(medicamentoResponse);

        Mockito.when(repository.save(medicamentoAtualizadoMapped))
                .thenReturn(medicamentoSalvo1);

        MedicamentoResponse result = mapper.medicamentoToMedicamentoResponse(service.atualizarMedicamento(1L, mapper.requestToMedicamento(request)));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.paciente().id(), request.idPaciente()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(repository).existsById(1L);

        Mockito.verify(mapper).requestToMedicamento(request);

        Mockito.verify(mapper).medicamentoToMedicamentoResponse(medicamentoSalvo1);
    }

    @Test
    @DisplayName("Deve lançar o erro de medicamento não encontrado")
    void cadastrarMedicamentoNaoLocalizado() {

        Exception errorMessage = assertThrows(MedicamentoNotFoundExeception.class,
                () -> service.atualizarMedicamento(1L, mapper.requestToMedicamento(request)));

        assertEquals("Medicamento não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve excluir um medicamento")
    void excluirMedicamento() {
        Long id = 1L;

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(medicamentoSalvo1));

        service.deletarPorId(id);

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro medicamento não localizado quando tentar excluir medicamento não cadastrado")
    void excluirMedicamentoNaoEncontrado() {
        Exception errorMessage = assertThrows(MedicamentoNotFoundExeception.class,
                () -> service.deletarPorId(1L));

        assertEquals("Medicamento não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de medicamentos quando não for informado o nome do paciente")
    void listarTodosMedicamentos() {
        List<Medicamento> medicamentoList = new ArrayList<>();
        medicamentoList.add(medicamentoSalvo1);
        medicamentoList.add(medicamentoSalvo2);

        Mockito.when(repository.findAll())
                .thenReturn(medicamentoList);

        List<MedicamentoResponse> resultSemNomePaciente = service.listarMedicamentosPorPaciente(null);

        assertEquals(resultSemNomePaciente.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar o medicamento quando for informado o id do medicamento")
    void listarMedicamentoPorId() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(medicamentoSalvo1));

        Medicamento resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(resultado.getId(), 1L);
    }

    @Test
    @DisplayName("Deve retornar lista de medicamentos quando for informado o nome do paciente")
    void listarMedicamentosPorPaciente() {
        List<Optional<Medicamento>> optionalList = new ArrayList<>();
        optionalList.add(Optional.of(medicamentoSalvo1));
        optionalList.add(Optional.of(medicamentoSalvo2));

        Mockito.when(repository.findAllMedicamentosByPacienteNome("Paciente 1"))
                .thenReturn(optionalList);

        List<MedicamentoResponse> resultadoComNomePaciente = service.listarMedicamentosPorPaciente("Paciente 1");

        assertEquals(resultadoComNomePaciente.size(), 2);
    }
}