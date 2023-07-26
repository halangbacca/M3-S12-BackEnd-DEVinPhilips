package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.response.DietaResponse;
import com.medsoft.labmedial.dtos.response.MedicamentoResponse;
import com.medsoft.labmedial.exceptions.MedicamentoNotFoundExeception;
import com.medsoft.labmedial.mapper.DietaMapper;
import com.medsoft.labmedial.mapper.MedicamentoMapper;
import com.medsoft.labmedial.models.Dieta;
import com.medsoft.labmedial.models.Medicamento;
import com.medsoft.labmedial.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository repository;

    public Medicamento cadastrarMedicamento(Medicamento request) {

        return repository.save(request);

    }

    public Medicamento buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new MedicamentoNotFoundExeception("Medicamento não encontrado!"));

    }

    public Boolean deletarPorId(Long id) {

        repository.findById(id)
                .map(medicamento -> {
                    repository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new MedicamentoNotFoundExeception("Medicamento não encontrado!"));

        return false;
    }

    public Medicamento atualizarMedicamento(Long id, Medicamento request) {

        if (this.repository.existsById(id)) {
            request.setId(id);
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
