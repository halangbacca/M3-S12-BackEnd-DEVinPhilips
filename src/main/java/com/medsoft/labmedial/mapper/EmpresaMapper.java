package com.medsoft.labmedial.mapper;

import com.medsoft.labmedial.dtos.request.EmpresaRequest;
import com.medsoft.labmedial.dtos.response.EmpresaResponse;
import com.medsoft.labmedial.models.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {
    EmpresaMapper INSTANCE = Mappers.getMapper(EmpresaMapper.class);

    Empresa requestToEmpresa(EmpresaRequest request);

    EmpresaResponse empresaToResponse(Empresa empresa);
}
