package br.com.fiap.easyinsurance.repository;

import br.com.fiap.easyinsurance.domain.Proposta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Proposta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {}
