package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DietaRepository extends JpaRepository<Dieta, Long> {
  List<Optional<Dieta>> findAllDietasByPacienteNome(String nomePaciente);
}
