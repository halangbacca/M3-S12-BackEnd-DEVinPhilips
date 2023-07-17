package com.medsoft.labmedial.services;

import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.models.Medicamento;
import com.medsoft.labmedial.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository repository;

    public Medicamento cadastrarMedicamento(Medicamento request) {

        return repository.save(request);

    }

    public List<Medicamento> listarMedicamentos() {

        return repository.findAll();

    }

    public Medicamento buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundExeception("Medicamento não encontrado!"));

    }

    public Boolean deletarPorId(Long id) {

        repository.findById(id)
                .map( medicamento -> {
                    repository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new PacienteNotFoundExeception("Exame não encontrado!"));

        return false;
    }

    public Medicamento atualizarMedicamento(Long id, Medicamento request){

        if(this.repository.existsById(id)){
            request.setId(id);
            return this.repository.save(request);
        }
        throw new PacienteNotFoundExeception("Medicamento não encontrado!");

    }

}
