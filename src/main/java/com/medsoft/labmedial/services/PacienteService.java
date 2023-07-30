package com.medsoft.labmedial.services;

import com.medsoft.labmedial.enums.TipoOcorrencia;
import com.medsoft.labmedial.exceptions.PacienteConflictExeception;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @Autowired
    private UsuarioService usuarioService;

    public Paciente cadastrarPaciente(Paciente request, String token) {

        this.repository.findByCpf(request.getCpf()).map(paciente -> {
            throw new PacienteConflictExeception("Cpf Ja Cadastrado");
        });

        this.repository.findByEmail(request.getEmail()).map(paciente -> {
            throw new PacienteConflictExeception("E-mail Ja Cadastrado");
        });

        Paciente paciente = repository.save(request);

        String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

        ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "PACIENTE", paciente.getId(),
                paciente.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.INSERT));

        return paciente;

    }

    public List<Paciente> listarPacientes() {

        return repository.findAll();

    }

    public Paciente buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundExeception("Paciente não encontrado!"));

    }

    public Paciente deletarPorId(Long id, String token) {

        return repository.findById(id)
                .map( paciente -> {
                    repository.deleteById(id);

                    String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

                    ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "PACIENTE", paciente.getId(),
                            paciente.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.DELETE));

                    return paciente;
                        })
                .orElseThrow(() -> new PacienteNotFoundExeception("Paciente não encontrado!"));
    }

    public Paciente atualizarPaciente(Long id, Paciente request, String token){

        if(this.repository.existsById(id)){

            Paciente oldPaciente = buscarPorId(id);

            String nomeUsuario = usuarioService.buscarUsuarioToken(token).getNome();

            ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "PACIENTE", id,
                    request.toString(), oldPaciente.toString(), new Date(), nomeUsuario, TipoOcorrencia.UPDATE));
            request.setId(id);
            Paciente novoPaciente = this.repository.save(request);
            return novoPaciente;
        }
        throw new PacienteNotFoundExeception("Paciente não encontrado!");

    }

}
