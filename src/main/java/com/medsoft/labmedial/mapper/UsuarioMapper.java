package com.medsoft.labmedial.mapper;

import com.medsoft.labmedial.dtos.request.LoginRequest;
import com.medsoft.labmedial.dtos.request.UsuarioRequest;
import com.medsoft.labmedial.dtos.response.UsuarioResponse;
import com.medsoft.labmedial.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
    Usuario requestToUsuario(UsuarioRequest request);
    UsuarioResponse usuarioToResponse(Usuario usuario);
    Usuario loginToUsuario(LoginRequest loginRequest);
}
