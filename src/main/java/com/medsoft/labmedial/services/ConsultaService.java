package com.medsoft.labmedial.services;

import com.medsoft.labmedial.enums.TipoOcorrencia;
import com.medsoft.labmedial.exceptions.ConsultaNotFoundExeception;
import com.medsoft.labmedial.models.Consulta;
import com.medsoft.labmedial.models.Dieta;
import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @Autowired
    private UsuarioService usuarioService;

    public Consulta cadastrarConsulta(Consulta request, String token) {

        request.setSituacao(true);

        Consulta novaConsulta = repository.save(request);

        String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

        ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "CONSULTA", novaConsulta.getId(),
                novaConsulta.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.INSERT));

        return novaConsulta;

    }

    public List<Consulta> listarConsultas() {

        return repository.findAll();

    }

    public Consulta buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ConsultaNotFoundExeception("Consulta não encontrada!"));

    }

    public Boolean deletarPorId(Long id,String token) {

        repository.findById(id)
                .map(consulta -> {

                    String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

                    ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "CONSULTA", id,
                            consulta.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.DELETE));

                    repository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new ConsultaNotFoundExeception("Consulta não encontrada!"));

        return false;
    }

    public Consulta atualizarConsulta(Long id, Consulta request, String token) {

        if (this.repository.existsById(id)) {
            request.setId(id);

            Optional<Consulta> oldConsulta = repository.findById(id);
            Consulta novaConsulta = repository.save(request);


            String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

            ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "CONSULTA", novaConsulta.getId(),
                    novaConsulta.toString(), oldConsulta.get().toString(), new Date(), nomeUsuario, TipoOcorrencia.UPDATE));

            return novaConsulta;
        }
        throw new ConsultaNotFoundExeception("Consulta não encontrada!");

    }

}
