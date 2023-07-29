package com.medsoft.labmedial.services;

import com.medsoft.labmedial.exceptions.PacienteConflictExeception;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;

    public Paciente cadastrarPaciente(Paciente request) {

        this.repository.findByCpf(request.getCpf()).map(paciente -> {
            throw new PacienteConflictExeception("CPF já cadastrado!");
        });

        this.repository.findByEmail(request.getEmail()).map(paciente -> {
            throw new PacienteConflictExeception("E-mail já cadastrado!");
        });

        return repository.save(request);

    }

    public List<Paciente> listarPacientes() {

        return repository.findAll();

    }

    public Paciente buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundExeception("Paciente não encontrado!"));

    }

    public Paciente deletarPorId(Long id) {

        return repository.findById(id)
                .map( paciente -> {
                    repository.deleteById(id);
                    return paciente;
                        })
                .orElseThrow(() -> new PacienteNotFoundExeception("Paciente não encontrado!"));
    }

    public Paciente atualizarPaciente(Long id, Paciente request) {

        if (this.repository.existsById(id)) {
            request.setId(id);
            return this.repository.save(request);
        }
        throw new PacienteNotFoundExeception("Paciente não encontrado!");

    }

}
