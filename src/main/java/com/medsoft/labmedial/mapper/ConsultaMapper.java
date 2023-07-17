package com.medsoft.labmedial.mapper;

import com.medsoft.labmedial.dtos.request.ConsultaRequest;
import com.medsoft.labmedial.dtos.response.ConsultaResponse;
import com.medsoft.labmedial.models.Consulta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {
    ConsultaMapper INSTANCE = Mappers.getMapper(ConsultaMapper.class);
    Consulta requestToConsulta(ConsultaRequest request);
    ConsultaResponse consultaToResponse(Consulta consulta);
}
