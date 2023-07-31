package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findAllConsultasByPacienteNome(String nomePaciente);

    List<Optional<Consulta>> findAllConsultasByPacienteId(Long id);

}
