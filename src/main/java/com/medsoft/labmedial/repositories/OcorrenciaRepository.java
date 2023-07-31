package com.medsoft.labmedial.repositories;

import com.medsoft.labmedial.models.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    List<Ocorrencia> findByCodLinkAndTabLink(Long codLink, String tabLink);
}
