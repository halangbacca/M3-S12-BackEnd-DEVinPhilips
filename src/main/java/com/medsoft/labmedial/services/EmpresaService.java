package com.medsoft.labmedial.services;

import com.medsoft.labmedial.exceptions.EmpresaNotFoundException;
import com.medsoft.labmedial.models.Empresa;
import com.medsoft.labmedial.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository repository;

    public Empresa cadastrarEmpresa(Empresa request) {
        return repository.save(request);
    }

    public List<Empresa> listarEmpresas() {
        return repository.findAll();
    }

    public Empresa buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmpresaNotFoundException("Empresa não encontrada!"));
    }

    public Boolean deletarPorId(Long id) {
        repository.findById(id)
                .map(empresa -> {
                    repository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new EmpresaNotFoundException("Empresa não encontrada!"));
        return false;
    }

    public Empresa atualizarEmpresa(Long id, Empresa request) {
        if (this.repository.existsById(id)) {
            request.setId(id);
            Empresa novaEmpresa = this.repository.save(request);
            return novaEmpresa;
        }
        throw new EmpresaNotFoundException("Empresa não encontrada!");
    }

}
