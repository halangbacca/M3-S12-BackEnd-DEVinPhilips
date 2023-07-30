package com.medsoft.labmedial.services;

import com.medsoft.labmedial.enums.TipoOcorrencia;
import com.medsoft.labmedial.exceptions.ExameNotFoundException;
import com.medsoft.labmedial.models.Exame;
import com.medsoft.labmedial.models.Exercicio;
import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.repositories.ExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExameService {

    @Autowired
    private ExameRepository repository;

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @Autowired
    private UsuarioService usuarioService;

    public Exame cadastrarExame(Exame request, String token) {
        request.setSituacao(true);

        String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

        Exame exame = repository.save(request);

        ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "EXAME", exame.getId(),
                exame.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.INSERT));
        return exame;

    }

    public List<Exame> listarExames(String nomePaciente) {
        if (nomePaciente == null) {
            return repository.findAll();
        } else {
            return repository.findAllDietasByPacienteNome(nomePaciente);
        }
    }

    public Exame buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ExameNotFoundException("Exame não encontrado!"));

    }

    public Boolean deletarPorId(Long id, String token) {

        repository.findById(id)
                .map(exame -> {
                    repository.deleteById(id);
                    String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

                    ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "EXAME", id,
                            exame.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.DELETE));

                    return true;
                })
                .orElseThrow(() -> new ExameNotFoundException("Exame não encontrado!"));

        return false;
    }

    public Exame atualizarExame(Long id, Exame request, String token) {

        if (this.repository.existsById(id)) {
            request.setId(id);

            Optional<Exame> odExame = repository.findById(id);
            Exame novoExame = repository.save(request);
            String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

            ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "EXAME", id,
                    novoExame.toString(),odExame.get().toString() , new Date(), nomeUsuario, TipoOcorrencia.UPDATE));

            return novoExame;
        }
        throw new ExameNotFoundException("Exame não encontrado!");

    }

}
