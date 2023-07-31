package com.medsoft.labmedial.services;

import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.repositories.OcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    public void cadastrarOcorrencia(Ocorrencia ocorrencia){
        this.ocorrenciaRepository.save(ocorrencia);
    }

    public List<Ocorrencia> buscarOcorrencia(Long codLink, String tabLink){
        return this.ocorrenciaRepository.findByCodLinkAndTabLink(codLink,tabLink);
    }

    public List<Ocorrencia> listarOcorrencia(){
        return this.ocorrenciaRepository.findAll();
    }


}
