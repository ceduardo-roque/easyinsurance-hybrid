package br.com.fiap.easyinsurance.service.impl;

import br.com.fiap.easyinsurance.domain.Proposta;
import br.com.fiap.easyinsurance.repository.PropostaRepository;
import br.com.fiap.easyinsurance.service.PropostaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Proposta}.
 */
@Service
@Transactional
public class PropostaServiceImpl implements PropostaService {

    private final Logger log = LoggerFactory.getLogger(PropostaServiceImpl.class);

    private final PropostaRepository propostaRepository;

    public PropostaServiceImpl(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @Override
    public Proposta save(Proposta proposta) {
        log.debug("Request to save Proposta : {}", proposta);
        return propostaRepository.save(proposta);
    }

    @Override
    public Optional<Proposta> partialUpdate(Proposta proposta) {
        log.debug("Request to partially update Proposta : {}", proposta);

        return propostaRepository
            .findById(proposta.getId())
            .map(
                existingProposta -> {
                    if (proposta.getNumeroProposta() != null) {
                        existingProposta.setNumeroProposta(proposta.getNumeroProposta());
                    }
                    if (proposta.getDataProposta() != null) {
                        existingProposta.setDataProposta(proposta.getDataProposta());
                    }
                    if (proposta.getValorProposta() != null) {
                        existingProposta.setValorProposta(proposta.getValorProposta());
                    }
                    if (proposta.getStatus() != null) {
                        existingProposta.setStatus(proposta.getStatus());
                    }

                    return existingProposta;
                }
            )
            .map(propostaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Proposta> findAll(Pageable pageable) {
        log.debug("Request to get all Propostas");
        return propostaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proposta> findOne(Long id) {
        log.debug("Request to get Proposta : {}", id);
        return propostaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proposta : {}", id);
        propostaRepository.deleteById(id);
    }
}
