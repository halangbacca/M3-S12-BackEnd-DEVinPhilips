package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
  List<Exame> findAllDietasByPacienteNome(String nomePaciente);
}
