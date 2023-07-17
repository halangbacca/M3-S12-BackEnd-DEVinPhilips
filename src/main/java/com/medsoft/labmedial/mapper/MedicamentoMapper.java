package com.medsoft.labmedial.mapper;

import com.medsoft.labmedial.dtos.request.MedicamentoRequest;
import com.medsoft.labmedial.dtos.response.MedicamentoResponse;
import com.medsoft.labmedial.models.Exame;
import com.medsoft.labmedial.models.Medicamento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MedicamentoMapper {
    MedicamentoMapper INSTANCE = Mappers.getMapper(MedicamentoMapper.class);
    Medicamento requestToMedicamento(MedicamentoRequest request);
    MedicamentoResponse medicamnetoToResponse(Medicamento medicamento);
}
