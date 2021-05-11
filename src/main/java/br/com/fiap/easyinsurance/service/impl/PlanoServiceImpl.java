package br.com.fiap.easyinsurance.service.impl;

import br.com.fiap.easyinsurance.domain.Plano;
import br.com.fiap.easyinsurance.repository.PlanoRepository;
import br.com.fiap.easyinsurance.service.PlanoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Plano}.
 */
@Service
@Transactional
public class PlanoServiceImpl implements PlanoService {

    private final Logger log = LoggerFactory.getLogger(PlanoServiceImpl.class);

    private final PlanoRepository planoRepository;

    public PlanoServiceImpl(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    @Override
    public Plano save(Plano plano) {
        log.debug("Request to save Plano : {}", plano);
        return planoRepository.save(plano);
    }

    @Override
    public Optional<Plano> partialUpdate(Plano plano) {
        log.debug("Request to partially update Plano : {}", plano);

        return planoRepository
            .findById(plano.getId())
            .map(
                existingPlano -> {
                    if (plano.getNomePlano() != null) {
                        existingPlano.setNomePlano(plano.getNomePlano());
                    }

                    return existingPlano;
                }
            )
            .map(planoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Plano> findAll(Pageable pageable) {
        log.debug("Request to get all Planos");
        return planoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plano> findOne(Long id) {
        log.debug("Request to get Plano : {}", id);
        return planoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plano : {}", id);
        planoRepository.deleteById(id);
    }
}
