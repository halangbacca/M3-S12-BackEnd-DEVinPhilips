package com.medsoft.labmedial.services;

import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public Usuario cadastrarUsuario(Usuario request) {


        return repository.save(request);

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
                    repository.deleteById(id);
                    return true;
                        })
                .orElseThrow(() -> new PacienteNotFoundExeception("Usuário não encontrado!"));

        return false;
    }

    public Usuario atualizarUsuario(Long id, Usuario request){

        if(this.repository.existsById(id)){
            request.setId(id);
            Usuario novoUsuario = this.repository.save(request);
            return novoUsuario;
        }
        throw new PacienteNotFoundExeception("Usuario não encontrado!");

    }

}
