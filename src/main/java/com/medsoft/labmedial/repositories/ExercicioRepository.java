package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
    List<Optional<Exercicio>> findAllExerciciosByPacienteNome(String nomePaciente);

    List<Optional<Exercicio>> findAllExerciciosByPacienteId(Long id);
}
