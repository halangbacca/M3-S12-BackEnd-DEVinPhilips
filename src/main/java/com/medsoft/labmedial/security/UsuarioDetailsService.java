package com.medsoft.labmedial.security;

import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findByEmail(String email){
        return this.usuarioRepository.findByEmail(email);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> opUsuario = usuarioRepository.findByEmail(email);

        if(opUsuario.isEmpty()){
            throw new UsernameNotFoundException("E-mail não encontrado: " + email);
        }

        Usuario user = opUsuario.get();

        return new User(user.getEmail(), user.getSenha(), new ArrayList<>());
    }

}