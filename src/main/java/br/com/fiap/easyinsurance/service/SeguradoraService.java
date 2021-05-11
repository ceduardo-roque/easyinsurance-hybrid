package br.com.fiap.easyinsurance.service;

import br.com.fiap.easyinsurance.domain.Seguradora;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Seguradora}.
 */
public interface SeguradoraService {
    /**
     * Save a seguradora.
     *
     * @param seguradora the entity to save.
     * @return the persisted entity.
     */
    Seguradora save(Seguradora seguradora);

    /**
     * Partially updates a seguradora.
     *
     * @param seguradora the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Seguradora> partialUpdate(Seguradora seguradora);

    /**
     * Get all the seguradoras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Seguradora> findAll(Pageable pageable);

    /**
     * Get the "id" seguradora.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Seguradora> findOne(Long id);

    /**
     * Delete the "id" seguradora.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
