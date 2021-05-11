package br.com.fiap.easyinsurance.service.impl;

import br.com.fiap.easyinsurance.domain.Seguradora;
import br.com.fiap.easyinsurance.repository.SeguradoraRepository;
import br.com.fiap.easyinsurance.service.SeguradoraService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Seguradora}.
 */
@Service
@Transactional
public class SeguradoraServiceImpl implements SeguradoraService {

    private final Logger log = LoggerFactory.getLogger(SeguradoraServiceImpl.class);

    private final SeguradoraRepository seguradoraRepository;

    public SeguradoraServiceImpl(SeguradoraRepository seguradoraRepository) {
        this.seguradoraRepository = seguradoraRepository;
    }

    @Override
    public Seguradora save(Seguradora seguradora) {
        log.debug("Request to save Seguradora : {}", seguradora);
        return seguradoraRepository.save(seguradora);
    }

    @Override
    public Optional<Seguradora> partialUpdate(Seguradora seguradora) {
        log.debug("Request to partially update Seguradora : {}", seguradora);

        return seguradoraRepository
            .findById(seguradora.getId())
            .map(
                existingSeguradora -> {
                    if (seguradora.getNomeSeguradora() != null) {
                        existingSeguradora.setNomeSeguradora(seguradora.getNomeSeguradora());
                    }

                    return existingSeguradora;
                }
            )
            .map(seguradoraRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Seguradora> findAll(Pageable pageable) {
        log.debug("Request to get all Seguradoras");
        return seguradoraRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seguradora> findOne(Long id) {
        log.debug("Request to get Seguradora : {}", id);
        return seguradoraRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seguradora : {}", id);
        seguradoraRepository.deleteById(id);
    }
}
