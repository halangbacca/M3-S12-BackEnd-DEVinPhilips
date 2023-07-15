package com.medsoft.labmedial.services;

import com.medsoft.labmedial.exceptions.PacienteConflictExeception;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;

    public Paciente cadastrarPaciente(Paciente request) {

        this.repository.findByCpf(request.getCpf()).map(paciente -> {
            throw new PacienteConflictExeception("Cpf Ja Cadastrado");
        });

        this.repository.findByEmail(request.getEmail()).map(paciente -> {
            throw new PacienteConflictExeception("E-mail Ja Cadastrado");
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

    public Boolean deletarPorId(Long id) {

        repository.findById(id)
                .map( paciente -> {
                    repository.deleteById(id);
                    return true;
                        })
                .orElseThrow(() -> new PacienteNotFoundExeception("Paciente não encontrado!"));

        return false;
    }

}
