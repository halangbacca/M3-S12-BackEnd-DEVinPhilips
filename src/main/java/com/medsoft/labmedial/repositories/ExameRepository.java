package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
    List<Exame> findAllExamesByPacienteNome(String nomePaciente);

    List<Optional<Exame>> findAllExamesByPacienteId(Long id);

}
