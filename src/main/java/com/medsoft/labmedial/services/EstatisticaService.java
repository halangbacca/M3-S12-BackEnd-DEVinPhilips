package com.medsoft.labmedial.services;

import com.medsoft.labmedial.models.Estatistica;
import com.medsoft.labmedial.repositories.EstatisticaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstatisticaService {

    private final EstatisticaRepository repository;

    public EstatisticaService(EstatisticaRepository repository) {
        this.repository = repository;
    }

    public List<Estatistica> listarEstatistica()  {

        return repository.findAll();
    }
}
