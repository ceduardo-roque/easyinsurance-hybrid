package br.com.fiap.easyinsurance.service;

import br.com.fiap.easyinsurance.domain.Proposta;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Proposta}.
 */
public interface PropostaService {
    /**
     * Save a proposta.
     *
     * @param proposta the entity to save.
     * @return the persisted entity.
     */
    Proposta save(Proposta proposta);

    /**
     * Partially updates a proposta.
     *
     * @param proposta the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Proposta> partialUpdate(Proposta proposta);

    /**
     * Get all the propostas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Proposta> findAll(Pageable pageable);

    /**
     * Get the "id" proposta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Proposta> findOne(Long id);

    /**
     * Delete the "id" proposta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
