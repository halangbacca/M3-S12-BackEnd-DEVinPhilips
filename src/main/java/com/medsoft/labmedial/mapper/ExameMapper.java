package com.medsoft.labmedial.mapper;

import com.medsoft.labmedial.dtos.request.ExameRequest;
import com.medsoft.labmedial.dtos.response.ExameResponse;
import com.medsoft.labmedial.models.Exame;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ExameMapper {
    ExameMapper INSTANCE = Mappers.getMapper(ExameMapper.class);

    Exame requestToExame(ExameRequest request);

    ExameResponse exameToResponse(Exame exame);

    default Exame optionalExameToExame(Optional<Exame> optionalExame) {
        return optionalExame.orElse(null);
    }
}
