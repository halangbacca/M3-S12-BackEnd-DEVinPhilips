package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.DietaRequest;
import com.medsoft.labmedial.dtos.response.DietaResponse;
import com.medsoft.labmedial.exceptions.DietaNotFoundException;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.DietaMapper;
import com.medsoft.labmedial.models.Dieta;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.repositories.DietaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DietaService {

  private final PacienteService service;

  private final DietaRepository repository;
  private final DietaMapper mapper;

  @Autowired
  public DietaService(DietaRepository repository,
                      PacienteService service,
                      DietaMapper mapper) {
    this.service = service;
    this.repository = repository;
    this.mapper = mapper;
  }

  public DietaResponse cadastrarDieta(DietaRequest request) {
    Paciente paciente = this.service.buscarPorId(request.idPaciente());

    if (paciente != null) {
      Dieta dieta = mapper.dietaRequestToDieta(request);
      dieta.setPaciente(paciente);
      dieta.setSituacao(true);
      return mapper.dietaToDietaResponse(repository.save(dieta));
    } else {
      throw new PacienteNotFoundExeception("Paciente não encontrado.");
    }
  }

  public DietaResponse atualizarDieta(DietaRequest request, Long id) {
    Optional<Dieta> optionalDieta = repository.findById(id);

    if (optionalDieta.isPresent()) {
      Dieta dieta = mapper.dietaRequestToDieta(request);
      dieta.setId(id);
      dieta.setSituacao(optionalDieta.get().getSituacao());
      return mapper.dietaToDietaResponse(repository.save(dieta));
    } else {
      throw new DietaNotFoundException("Dieta não encontrada.");
    }
  }

  public void excluirDieta(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new DietaNotFoundException("Dieta não encontrada.");
    }
  }

  public List<DietaResponse> listarDietasPorPaciente(String nomePaciente) {
    if (nomePaciente == null) {
      return repository.findAll()
              .stream()
              .map(DietaMapper.INSTANCE::dietaToDietaResponse)
              .collect(Collectors.toList());
    } else {
      List<Optional<Dieta>> dietas = repository.findAllDietasByPacienteNome(nomePaciente);
      return dietas.stream()
              .map(DietaMapper.INSTANCE::optionalDietaToDieta)
              .map(DietaMapper.INSTANCE::dietaToDietaResponse)
              .collect(Collectors.toList());
    }
  }
}
