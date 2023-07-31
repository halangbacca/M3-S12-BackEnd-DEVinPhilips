package com.medsoft.labmedial.mapper;

import com.medsoft.labmedial.dtos.request.DietaRequest;
import com.medsoft.labmedial.dtos.response.DietaResponse;
import com.medsoft.labmedial.models.Dieta;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface DietaMapper {
  DietaMapper INSTANCE = Mappers.getMapper(DietaMapper.class);
  
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "idPaciente", target = "paciente.id")
  @Mapping(target = "situacao", ignore = true)
  Dieta dietaRequestToDieta(DietaRequest request);

  DietaResponse dietaToDietaResponse(Dieta dieta);

  default Dieta optionalDietaToDieta(Optional<Dieta> optionalDieta) {
    return optionalDieta.orElse(null);
  }
}
