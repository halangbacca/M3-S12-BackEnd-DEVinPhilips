package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    List<Optional<Medicamento>> findAllMedicamentosByPacienteNome(String nomePaciente);

    List<Optional<Medicamento>> findAllMedicamentosByPacienteId(Long id);

}
