package com.medsoft.labmedial.mapper;

import com.medsoft.labmedial.dtos.request.ExercicioRequest;
import com.medsoft.labmedial.dtos.response.ExercicioResponse;
import com.medsoft.labmedial.models.Exercicio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ExercicioMapper {
  ExercicioMapper INSTANCE = Mappers.getMapper(ExercicioMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "situacao", ignore = true)
  @Mapping(source = "idPaciente", target = "paciente.id")
  Exercicio exercicioRequestToExercicio(ExercicioRequest request);

  ExercicioResponse exercicioToExercicioResponse(Exercicio exercicio);

  default Exercicio optionalExercicioToExercicio(Optional<Exercicio> optionalExercicio) {
    return optionalExercicio.orElse(null);
  }
}
