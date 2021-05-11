package br.com.fiap.easyinsurance.service.impl;

import br.com.fiap.easyinsurance.domain.Corretor;
import br.com.fiap.easyinsurance.repository.CorretorRepository;
import br.com.fiap.easyinsurance.service.CorretorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Corretor}.
 */
@Service
@Transactional
public class CorretorServiceImpl implements CorretorService {

    private final Logger log = LoggerFactory.getLogger(CorretorServiceImpl.class);

    private final CorretorRepository corretorRepository;

    public CorretorServiceImpl(CorretorRepository corretorRepository) {
        this.corretorRepository = corretorRepository;
    }

    @Override
    public Corretor save(Corretor corretor) {
        log.debug("Request to save Corretor : {}", corretor);
        return corretorRepository.save(corretor);
    }

    @Override
    public Optional<Corretor> partialUpdate(Corretor corretor) {
        log.debug("Request to partially update Corretor : {}", corretor);

        return corretorRepository
            .findById(corretor.getId())
            .map(
                existingCorretor -> {
                    if (corretor.getNomeCorretor() != null) {
                        existingCorretor.setNomeCorretor(corretor.getNomeCorretor());
                    }
                    if (corretor.getUsuario() != null) {
                        existingCorretor.setUsuario(corretor.getUsuario());
                    }
                    if (corretor.getDataNascimento() != null) {
                        existingCorretor.setDataNascimento(corretor.getDataNascimento());
                    }
                    if (corretor.getFoto() != null) {
                        existingCorretor.setFoto(corretor.getFoto());
                    }
                    if (corretor.getFotoContentType() != null) {
                        existingCorretor.setFotoContentType(corretor.getFotoContentType());
                    }
                    if (corretor.getTelefone() != null) {
                        existingCorretor.setTelefone(corretor.getTelefone());
                    }

                    return existingCorretor;
                }
            )
            .map(corretorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Corretor> findAll(Pageable pageable) {
        log.debug("Request to get all Corretors");
        return corretorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Corretor> findOne(Long id) {
        log.debug("Request to get Corretor : {}", id);
        return corretorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Corretor : {}", id);
        corretorRepository.deleteById(id);
    }
}
