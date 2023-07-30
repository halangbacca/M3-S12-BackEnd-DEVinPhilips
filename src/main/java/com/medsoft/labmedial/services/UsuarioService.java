package com.medsoft.labmedial.services;

import com.medsoft.labmedial.enums.TipoOcorrencia;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.exceptions.UsuarioExeception;
import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.repositories.UsuarioRepository;
import com.medsoft.labmedial.security.JWTUtil;
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

    @Autowired
    private JWTUtil jwtUtil;

    public Usuario cadastrarUsuario(Usuario request, String token) {

        Usuario usuario = repository.save(request);

        if (token != null) {
            String nomeUsuario = buscarUsuarioToken(token).getNome();

            ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "USUARIO", usuario.getId(),
                    usuario.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.INSERT));
        }

        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundExeception("Usuário não encontrado!"));
    }

    public Boolean deletarPorId(Long id, String token) {

        repository.findById(id)
                .map(usuario -> {

                    String nomeUsuario = buscarUsuarioToken(token).getNome();

                    ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "USUARIO", usuario.getId(),
                            usuario.toString(), null, new Date(), nomeUsuario, TipoOcorrencia.DELETE));

                    repository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new PacienteNotFoundExeception("Usuário não encontrado!"));

        return false;
    }

    public Usuario atualizarUsuario(Long id, Usuario request, String token) {

        if (this.repository.existsById(id)) {

            Usuario oldUsuario = buscarPorId(id);

            String nomeUsuario = buscarUsuarioToken(token).getNome();

            ocorrenciaService.cadastrarOcorrencia(new Ocorrencia(null, "USUARIO", id,
                    request.toString(), oldUsuario.toString(), new Date(), nomeUsuario, TipoOcorrencia.UPDATE));

            request.setId(id);
            Usuario novoUsuario = this.repository.save(request);
            return novoUsuario;
        }
        throw new PacienteNotFoundExeception("Usuário não encontrado!");

    }


    public Optional<Usuario> buscarEmail(String email) {
        return repository.findByEmail(email);
    }

    public Boolean resetarSenha(Long id, Usuario request) {
        repository.findById(id)
                .map(usuario -> {
                    usuario.setSenha(request.getSenha());
                    this.repository.save(usuario);
                    return true;
                })
                .orElseThrow(() -> new PacienteNotFoundExeception("Usuário não encontrado!"));
        return false;
    }

    public Usuario buscarUsuarioToken(String autorization) {
        String token = autorization.substring(7);
        String email = jwtUtil.validateTokenAndRetrieveSubject(token);

        Optional<Usuario> usuario = buscarEmail(email);

        if (usuario.isEmpty()) {
            throw new UsuarioExeception("Usuário não encontrado!");
        }

        return usuario.get();
    }

}
