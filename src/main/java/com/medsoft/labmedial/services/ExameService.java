package com.medsoft.labmedial.services;

import com.medsoft.labmedial.exceptions.ExameNotFoundException;
import com.medsoft.labmedial.models.Exame;
import com.medsoft.labmedial.repositories.ExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExameService {

    @Autowired
    private ExameRepository repository;

    public Exame cadastrarExame(Exame request) {
        request.setSituacao(true);
        return repository.save(request);

    }

    public List<Exame> listarExames() {
        return repository.findAll();
    }

    public Exame buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ExameNotFoundException("Exame não encontrado!"));

    }

    public Boolean deletarPorId(Long id) {

        repository.findById(id)
                .map(paciente -> {
                    repository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new ExameNotFoundException("Exame não encontrado!"));

        return false;
    }

    public Exame atualizarExame(Long id, Exame request) {

        if (this.repository.existsById(id)) {
            request.setId(id);
            return this.repository.save(request);
        }
        throw new ExameNotFoundException("Exame não encontrado!");

    }

}
