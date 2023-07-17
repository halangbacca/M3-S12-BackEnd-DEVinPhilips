package com.medsoft.labmedial.services;

import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.models.Consulta;
import com.medsoft.labmedial.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    public Consulta cadastrarConsulta(Consulta request) {

        return repository.save(request);

    }

    public List<Consulta> listarConsultas() {

        return repository.findAll();

    }

    public Consulta buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundExeception("Consulta não encontrado!"));

    }

    public Boolean deletarPorId(Long id) {

        repository.findById(id)
                .map( consulta -> {
                    repository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new PacienteNotFoundExeception("Consulta não encontrado!"));

        return false;
    }

    public Consulta atualizarConsulta(Long id, Consulta request){

        if(this.repository.existsById(id)){
            request.setId(id);
            return this.repository.save(request);
        }
        throw new PacienteNotFoundExeception("Paciente não encontrado!");

    }

}
