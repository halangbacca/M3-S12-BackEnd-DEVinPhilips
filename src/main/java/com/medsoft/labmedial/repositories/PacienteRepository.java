package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

  Optional<Paciente> findByCpf(String cpf);

  Optional<Paciente> findByEmail(String email);
}
