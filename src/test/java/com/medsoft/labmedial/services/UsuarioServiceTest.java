package com.medsoft.labmedial.services;

import com.medsoft.labmedial.dtos.request.SenhaRequest;
import com.medsoft.labmedial.dtos.request.UsuarioRequest;
import com.medsoft.labmedial.dtos.response.UsuarioResponse;
import com.medsoft.labmedial.enums.NivelUsuario;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.exceptions.UsuarioExeception;
import com.medsoft.labmedial.mapper.UsuarioMapper;
import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.repositories.UsuarioRepository;
import com.medsoft.labmedial.security.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class UsuarioServiceTest {
    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @Mock
    OcorrenciaService ocorrenciaService;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private UsuarioService service;

    private UsuarioRequest request;

    private SenhaRequest senhaRequest;
    private Usuario usuario;
    private Usuario usuarioSalvo1;
    private Usuario usuarioSalvo2;

    private UsuarioResponse usuarioResponse;
    private Usuario usuarioAtualizadoMapped;

    @BeforeEach
    void setUp() {
        request = new UsuarioRequest(
                "Usuário 1",
                "Masculino",
                "313.626.170-40",
                "47997200499",
                "halan@email.com",
                "teste",
                NivelUsuario.ADMINISTRADOR,
                null
        );

        usuario = Mockito.mock(Usuario.class);
        usuario.setId(1L);
        usuario.setNome("Usuário 1");

        usuarioAtualizadoMapped = Mockito.mock(Usuario.class);

        usuarioSalvo1 = new Usuario(
                1L,
                "Usuário 1",
                "Masculino",
                "313.626.170-40",
                "47997200499",
                "halan@email.com",
                "teste",
                NivelUsuario.ADMINISTRADOR,
                true
        );

        usuarioSalvo2 = new Usuario(
                1L,
                "Usuário 1",
                "Masculino",
                "313.626.170-40",
                "47997200499",
                "halan@email.com",
                "teste",
                NivelUsuario.ADMINISTRADOR,
                true
        );

        usuarioResponse = new UsuarioResponse(
                1L,
                "Usuário 1",
                "Masculino",
                "313.626.170-40",
                "47997200499",
                "halan@email.com",
                "teste",
                NivelUsuario.ADMINISTRADOR,
                true
        );

        senhaRequest = new SenhaRequest(
                1L,
                "halan@email.com",
                "123456789"
        );
    }

    @Test
    @DisplayName("Deve retornar o usuário salvo")
    void cadastrarUsuario() {

        Mockito.when(mapper.requestToUsuario(request))
                .thenReturn(usuarioAtualizadoMapped);

        Mockito.when(mapper.usuarioToResponse(usuarioSalvo1))
                .thenReturn(usuarioResponse);

        Mockito.when(repository.save(usuarioAtualizadoMapped))
                .thenReturn(usuarioSalvo1);

        Mockito.when(repository.findByEmail(jwtUtil.generateToken(usuarioSalvo1)))
                .thenReturn(Optional.ofNullable(usuarioSalvo1));

        UsuarioResponse result = mapper.usuarioToResponse(service.cadastrarUsuario(mapper.requestToUsuario(request), "1234567890"));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.cpf(), request.cpf()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(mapper).requestToUsuario(request);

        Mockito.verify(mapper).usuarioToResponse(usuarioSalvo1);
    }

    @Test
    @DisplayName("Deve atualizar o usuário e retornar o usuário salvo")
    void atualizarUsuario() {
        Mockito.when(repository.existsById(1L))
                .thenReturn(true);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(usuarioSalvo1));

        Mockito.when(mapper.requestToUsuario(request))
                .thenReturn(usuarioAtualizadoMapped);

        Mockito.when(mapper.usuarioToResponse(usuarioSalvo1))
                .thenReturn(usuarioResponse);

        Mockito.when(repository.save(usuarioAtualizadoMapped))
                .thenReturn(usuarioSalvo1);

        Mockito.when(repository.findByEmail(jwtUtil.generateToken(usuarioSalvo1)))
                .thenReturn(Optional.ofNullable(usuarioSalvo1));

        UsuarioResponse result = mapper.usuarioToResponse(service.atualizarUsuario(1L, mapper.requestToUsuario(request), "1234567890"));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.cpf(), request.cpf()),
                () -> assertEquals(result.situacao(), true)
        );

        Mockito.verify(repository).existsById(1L);

        Mockito.verify(mapper).requestToUsuario(request);

        Mockito.verify(mapper).usuarioToResponse(usuarioSalvo1);
    }

    @Test
    @DisplayName("Deve lançar o erro de usuário não encontrado")
    void cadastrarUsuarioNaoLocalizado() {

        Exception errorMessage = assertThrows(PacienteNotFoundExeception.class,
                () -> service.atualizarUsuario(1L, mapper.requestToUsuario(request), "1234567890"));

        assertEquals("Usuário não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve excluir um usuário")
    void excluirUsuario() {
        Long id = 1L;

        Mockito.when(repository.findByEmail(jwtUtil.generateToken(usuarioSalvo1)))
                .thenReturn(Optional.ofNullable(usuarioSalvo1));

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(usuarioSalvo1));

        service.deletarPorId(id, "1234567890");

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro usuário não localizado quando tentar excluir usuário não cadastrado")
    void excluirUsuarioNaoEncontrado() {
        Exception errorMessage = assertThrows(PacienteNotFoundExeception.class,
                () -> service.deletarPorId(1L, "123456"));

        assertEquals("Usuário não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de usuários")
    void listarTodosUsuarios() {
        List<Usuario> usuarioList = new ArrayList<>();
        usuarioList.add(usuarioSalvo1);
        usuarioList.add(usuarioSalvo2);

        Mockito.when(repository.findAll())
                .thenReturn(usuarioList);

        List<Usuario> result = service.listarUsuarios();

        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar o usuário quando for informado o id do usuário")
    void listarUsuarioPorId() {
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.ofNullable(usuarioSalvo1));

        Usuario resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(resultado.getId(), 1L);
    }

    @Test
    @DisplayName("Deve lançar usuário não encontrado quando tentar alterar a senha de um usuário não cadastrado")
    void alterarSenhaUsuarioNaoEncontrado() {
        Exception errorMessage = assertThrows(PacienteNotFoundExeception.class,
                () -> service.resetarSenha(1L, senhaRequest));

        assertEquals("Usuário não encontrado!", errorMessage.getMessage());
    }

    @Test
    @DisplayName("Deve retornar o token do usuário")
    void listarTokenUsuario() {
        Mockito.when(repository.findByEmail(jwtUtil.generateToken(usuarioSalvo1)))
                .thenReturn(Optional.ofNullable(usuarioSalvo1));

        Usuario resultado = service.buscarUsuarioToken("1234567890");

        assertNotNull(resultado);
        assertEquals(resultado.getId(), 1L);
    }

    @Test
    @DisplayName("Deve lançar usuário não encontrado quando tentar buscar o token de um usuário não cadastrado")
    void buscarTokenUsuarioNaoEncontrado() {
        Exception errorMessage = assertThrows(UsuarioExeception.class,
                () -> service.buscarUsuarioToken("1234567890"));

        assertEquals("Usuário não encontrado!", errorMessage.getMessage());
    }
}