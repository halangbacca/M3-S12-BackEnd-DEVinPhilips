package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Consulta;
import com.medsoft.labmedial.models.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}
