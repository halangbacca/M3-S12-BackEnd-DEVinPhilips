package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Estatistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstatisticaRepository extends JpaRepository<Estatistica, Long> {
}
