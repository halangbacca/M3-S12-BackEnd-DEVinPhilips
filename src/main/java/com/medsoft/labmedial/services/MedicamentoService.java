package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.response.MedicamentoResponse;
import com.medsoft.labmedial.enums.TipoOcorrencia;
import com.medsoft.labmedial.exceptions.MedicamentoNotFoundExeception;
import com.medsoft.labmedial.mapper.MedicamentoMapper;
import com.medsoft.labmedial.models.Medicamento;
import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository repository;

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @Autowired
    private UsuarioService usuarioService;

    public Medicamento cadastrarMedicamento(Medicamento request, String token) {
        request.setSituacao(true);

        Medicamento medicamento = repository.save(request);

        String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

        ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "MEDICAMENTO", medicamento.getId(),
                request.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.INSERT));

        return medicamento;
    }

    public Medicamento buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new MedicamentoNotFoundExeception("Medicamento não encontrado!"));

    }

    public Boolean deletarPorId(Long id, String token) {

        repository.findById(id)
                .map(medicamento -> {
                    String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

                    ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "MEDICAMENTO", medicamento.getId(),
                            medicamento.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.DELETE));

                    repository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new MedicamentoNotFoundExeception("Medicamento não encontrado!"));

        return false;
    }

    public List<MedicamentoResponse> listarMedicamentosPorPacienteId(Long id) {
        List<Optional<Medicamento>> medicamentos = repository.findAllMedicamentosByPacienteId(id);
        return medicamentos.stream()
                .map(MedicamentoMapper.INSTANCE::optionalMedicamentoToMedicamento)
                .map(MedicamentoMapper.INSTANCE::medicamentoToMedicamentoResponse)
                .collect(Collectors.toList());
    }

    public Medicamento atualizarMedicamento(Long id, Medicamento request, String token) {
        if (this.repository.existsById(id)) {
            request.setSituacao(true);
            request.setId(id);
            Medicamento oldMedicamento = buscarPorId(id);

            String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

            ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "MEDICAMENTO", id,
                    request.toString(), oldMedicamento.toString(), new Date(), nomeUsuario, TipoOcorrencia.UPDATE));
            return this.repository.save(request);
        }
        throw new MedicamentoNotFoundExeception("Medicamento não encontrado!");
    }

    public List<MedicamentoResponse> listarMedicamentosPorPaciente(String nomePaciente) {

        List<Optional<Medicamento>> medicamentos = repository.findAllMedicamentosByPacienteNome(nomePaciente);

        if (nomePaciente == null) {
            return repository.findAll()
                    .stream()
                    .map(MedicamentoMapper.INSTANCE::medicamentoToMedicamentoResponse)
                    .collect(Collectors.toList());
        } else {
            return medicamentos.stream()
                    .map(MedicamentoMapper.INSTANCE::optionalMedicamentoToMedicamento)
                    .map(MedicamentoMapper.INSTANCE::medicamentoToMedicamentoResponse)
                    .collect(Collectors.toList());
        }
    }

}
