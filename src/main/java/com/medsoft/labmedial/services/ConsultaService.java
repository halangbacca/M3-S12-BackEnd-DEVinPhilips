package com.medsoft.labmedial.services;

import com.medsoft.labmedial.exceptions.ConsultaNotFoundExeception;
import com.medsoft.labmedial.models.Consulta;
import com.medsoft.labmedial.repositories.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public Consulta cadastrarConsulta(Consulta request) {

        request.setSituacao(true);

        return repository.save(request);

    }

    public List<Consulta> listarConsultas(String nomePaciente) {
        if (nomePaciente == null) {
            return repository.findAll();
        } else {
            return repository.findAllDietasByPacienteNome(nomePaciente);
        }
    }

    public Consulta buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ConsultaNotFoundExeception("Consulta não encontrada!"));

    }

    public Boolean deletarPorId(Long id) {

        repository.findById(id)
                .map(consulta -> {
                    repository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new ConsultaNotFoundExeception("Consulta não encontrada!"));

        return false;
    }

    public Consulta atualizarConsulta(Long id, Consulta request) {

        if (this.repository.existsById(id)) {
            request.setId(id);
            return this.repository.save(request);
        }
        throw new ConsultaNotFoundExeception("Consulta não encontrada!");

    }
}
