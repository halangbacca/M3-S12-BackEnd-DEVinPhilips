package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.ExercicioRequest;
import com.medsoft.labmedial.dtos.response.ExercicioResponse;
import com.medsoft.labmedial.exceptions.DietaNotFoundException;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.ExercicioMapper;
import com.medsoft.labmedial.models.Exercicio;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.repositories.ExercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExercicioService {
  private final ExercicioRepository repository;
  private final PacienteService service;
  private final ExercicioMapper mapper;

  @Autowired
  public ExercicioService(ExercicioRepository repository,
                          PacienteService service, ExercicioMapper mapper) {
    this.repository = repository;
    this.service = service;
    this.mapper = mapper;
  }

  public ExercicioResponse cadastrarExercicio(ExercicioRequest request) {
    Paciente paciente = service.buscarPorId(request.idPaciente());

    if (paciente != null) {
      Exercicio exercicio = mapper.exercicioRequestToExercicio(request);
      exercicio.setPaciente(paciente);
      exercicio.setSituacao(true);
      return mapper.exercicioToExercicioResponse(repository.save(exercicio));
    } else {
      throw new PacienteNotFoundExeception("Paciente não encontrado");
    }
  }

  public ExercicioResponse atualizarExercicio(ExercicioRequest request, Long id) {
    if (repository.existsById(id)) {
      Exercicio exercicio = mapper.exercicioRequestToExercicio(request);
      exercicio.setId(id);
      exercicio.setSituacao(repository.findById(id).get().getSituacao());
      return mapper.exercicioToExercicioResponse(repository.save(exercicio));
    } else {
      throw new DietaNotFoundException("Exercício não encontrado.");
    }

  }

  public void excluirExercicio(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new DietaNotFoundException("Exercício não encontrado.");
    }
  }

  public List<ExercicioResponse> listarExerciciosPorPaciente(String nomePaciente) {

    List<Optional<Exercicio>> exercicios = repository.findAllExerciciosByPacienteNome(nomePaciente);

    if (nomePaciente == null) {
      return repository.findAll()
              .stream()
              .map(ExercicioMapper.INSTANCE::exercicioToExercicioResponse)
              .collect(Collectors.toList());
    } else {
      return exercicios.stream()
              .map(ExercicioMapper.INSTANCE::optionalExercicioToExercicio)
              .map(ExercicioMapper.INSTANCE::exercicioToExercicioResponse)
              .collect(Collectors.toList());
    }
  }
}
