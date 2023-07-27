package com.medsoft.labmedial.mapper;

import com.medsoft.labmedial.dtos.request.PacienteRequest;
import com.medsoft.labmedial.dtos.request.SenhaRequest;
import com.medsoft.labmedial.dtos.request.UsuarioRequest;
import com.medsoft.labmedial.dtos.response.PacienteResponse;
import com.medsoft.labmedial.dtos.response.UsuarioResponse;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
    Usuario requestToUsuario(UsuarioRequest request);

    Usuario requestSenhaToUsuario(SenhaRequest request);
    UsuarioResponse usuarioToResponse(Usuario usuario);
}
