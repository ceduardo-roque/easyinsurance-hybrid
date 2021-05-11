package br.com.fiap.easyinsurance.repository;

import br.com.fiap.easyinsurance.domain.Seguradora;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Seguradora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeguradoraRepository extends JpaRepository<Seguradora, Long> {}
