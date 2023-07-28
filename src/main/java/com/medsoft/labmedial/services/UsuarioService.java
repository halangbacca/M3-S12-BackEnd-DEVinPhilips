package com.medsoft.labmedial.services;

import com.medsoft.labmedial.enums.TipoOcorrencia;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private OcorrenciaService ocorrenciaService;

    public Usuario cadastrarUsuario(Usuario request) {

        Usuario usuario = repository.save(request);

        ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null,"USUARIO", usuario.getId(),
                usuario.toString(),null,new Date(),null, TipoOcorrencia.INSERT));


        return usuario;

    }

    public List<Usuario> listarUsuarios() {

        return repository.findAll();

    }

    public Usuario buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundExeception("Usuário não encontrado!"));

    }

    public Boolean deletarPorId(Long id) {

        repository.findById(id)
                .map( usuario -> {

                    ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null,"USUARIO", usuario.getId(),
                            usuario.toString(),null,new Date(),null, TipoOcorrencia.DELETE));

                    repository.deleteById(id);
                    return true;
                        })
                .orElseThrow(() -> new PacienteNotFoundExeception("Usuário não encontrado!"));

        return false;
    }

    public Usuario atualizarUsuario(Long id, Usuario request){

        if(this.repository.existsById(id)){

            Usuario oldUsuario = buscarPorId(id);
            ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null,"USUARIO", id,
                    request.toString(),oldUsuario.toString(),new Date(),null, TipoOcorrencia.UPDATE));

            request.setId(id);
            Usuario novoUsuario = this.repository.save(request);
            return novoUsuario;
        }
        throw new PacienteNotFoundExeception("Usuario não encontrado!");

    }


    public Optional<Usuario> buscarEmail(String email){
        return repository.findByEmail(email);
    }

    public Boolean resetarSenha(Long id, Usuario request) {
        repository.findById(id)
                .map( usuario -> {
                    repository.findById(id);
                    usuario.setSenha(request.getSenha());
                    this.repository.save(usuario);
                    return true;
                })
                .orElseThrow(() -> new PacienteNotFoundExeception("Usuário não encontrado!"));
        return null;

    }

}
