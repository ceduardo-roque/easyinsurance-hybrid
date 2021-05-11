package br.com.fiap.easyinsurance.service;

import br.com.fiap.easyinsurance.domain.Corretor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Corretor}.
 */
public interface CorretorService {
    /**
     * Save a corretor.
     *
     * @param corretor the entity to save.
     * @return the persisted entity.
     */
    Corretor save(Corretor corretor);

    /**
     * Partially updates a corretor.
     *
     * @param corretor the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Corretor> partialUpdate(Corretor corretor);

    /**
     * Get all the corretors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Corretor> findAll(Pageable pageable);

    /**
     * Get the "id" corretor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Corretor> findOne(Long id);

    /**
     * Delete the "id" corretor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
