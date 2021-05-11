package br.com.fiap.easyinsurance.repository;

import br.com.fiap.easyinsurance.domain.Corretor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Corretor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorretorRepository extends JpaRepository<Corretor, Long> {}
