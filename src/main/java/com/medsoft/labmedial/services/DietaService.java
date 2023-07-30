package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.DietaRequest;
import com.medsoft.labmedial.dtos.response.DietaResponse;
import com.medsoft.labmedial.enums.TipoOcorrencia;
import com.medsoft.labmedial.exceptions.DietaNotFoundException;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.DietaMapper;
import com.medsoft.labmedial.models.Dieta;
import com.medsoft.labmedial.models.Exercicio;
import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.repositories.DietaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DietaService {

    private final PacienteService service;

    private final DietaRepository repository;
    private final DietaMapper mapper;
  private final OcorrenciaService ocorrenciaService;
  private final UsuarioService usuarioService;

    @Autowired
    public DietaService(DietaRepository repository,
                        PacienteService service,
                        DietaMapper mapper, OcorrenciaService ocorrenciaService, UsuarioService usuarioService) {
        this.service = service;
        this.repository = repository;
        this.mapper = mapper;
    this.ocorrenciaService = ocorrenciaService;
    this.usuarioService = usuarioService;
  }

    public DietaResponse cadastrarDieta(DietaRequest request, String token) {
        Paciente paciente = this.service.buscarPorId(request.idPaciente());

        if (paciente != null) {
            Dieta dieta = mapper.dietaRequestToDieta(request);
            dieta.setPaciente(paciente);
            dieta.setSituacao(true);
            Dieta novaDieta = repository.save(dieta);

      String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

      ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "DIETA", novaDieta.getId(),
              novaDieta.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.INSERT));

      return mapper.dietaToDietaResponse(novaDieta);
        } else {
            throw new PacienteNotFoundExeception("Paciente não encontrado.");
        }
    }

    public DietaResponse atualizarDieta(DietaRequest request, Long id, String token) {
        Optional<Dieta> optionalDieta = repository.findById(id);

        if (optionalDieta.isPresent()) {
            Dieta dieta = mapper.dietaRequestToDieta(request);
            dieta.setId(id);
            dieta.setSituacao(optionalDieta.get().getSituacao());
            Dieta novaDieta = repository.save(dieta);

      String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

      ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "DIETA", id,
              novaDieta.toString(), optionalDieta.get().toString(), new Date(), nomeUsuario, TipoOcorrencia.UPDATE));

      return mapper.dietaToDietaResponse(novaDieta);
        } else {
            throw new DietaNotFoundException("Dieta não encontrada.");
        }
    }

    public void excluirDieta(Long id, String token) {
        if (repository.existsById(id)) {
      String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

      ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "DIETA", id,
              repository.findById(id).toString(), null, new Date(), nomeUsuario, TipoOcorrencia.DELETE));
            repository.deleteById(id);
        } else {
            throw new DietaNotFoundException("Dieta não encontrada.");
        }
    }

    public Dieta listarDietaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DietaNotFoundException("Dieta não encontrada."));
    }

    public List<DietaResponse> listarDietasPorPacienteId(Long id) {
        List<Optional<Dieta>> dietas = repository.findAllDietasByPacienteId(id);
        return dietas.stream()
                .map(DietaMapper.INSTANCE::optionalDietaToDieta)
                .map(DietaMapper.INSTANCE::dietaToDietaResponse)
                .collect(Collectors.toList());
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
