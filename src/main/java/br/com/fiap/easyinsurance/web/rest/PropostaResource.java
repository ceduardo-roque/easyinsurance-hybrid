package br.com.fiap.easyinsurance.web.rest;

import br.com.fiap.easyinsurance.domain.Proposta;
import br.com.fiap.easyinsurance.repository.PropostaRepository;
import br.com.fiap.easyinsurance.service.PropostaService;
import br.com.fiap.easyinsurance.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.fiap.easyinsurance.domain.Proposta}.
 */
@RestController
@RequestMapping("/api")
public class PropostaResource {

    private final Logger log = LoggerFactory.getLogger(PropostaResource.class);

    private static final String ENTITY_NAME = "proposta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropostaService propostaService;

    private final PropostaRepository propostaRepository;

    public PropostaResource(PropostaService propostaService, PropostaRepository propostaRepository) {
        this.propostaService = propostaService;
        this.propostaRepository = propostaRepository;
    }

    /**
     * {@code POST  /propostas} : Create a new proposta.
     *
     * @param proposta the proposta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proposta, or with status {@code 400 (Bad Request)} if the proposta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/propostas")
    public ResponseEntity<Proposta> createProposta(@Valid @RequestBody Proposta proposta) throws URISyntaxException {
        log.debug("REST request to save Proposta : {}", proposta);
        if (proposta.getId() != null) {
            throw new BadRequestAlertException("A new proposta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Proposta result = propostaService.save(proposta);
        return ResponseEntity
            .created(new URI("/api/propostas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /propostas/:id} : Updates an existing proposta.
     *
     * @param id the id of the proposta to save.
     * @param proposta the proposta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proposta,
     * or with status {@code 400 (Bad Request)} if the proposta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proposta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/propostas/{id}")
    public ResponseEntity<Proposta> updateProposta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Proposta proposta
    ) throws URISyntaxException {
        log.debug("REST request to update Proposta : {}, {}", id, proposta);
        if (proposta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proposta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!propostaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Proposta result = propostaService.save(proposta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, proposta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /propostas/:id} : Partial updates given fields of an existing proposta, field will ignore if it is null
     *
     * @param id the id of the proposta to save.
     * @param proposta the proposta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proposta,
     * or with status {@code 400 (Bad Request)} if the proposta is not valid,
     * or with status {@code 404 (Not Found)} if the proposta is not found,
     * or with status {@code 500 (Internal Server Error)} if the proposta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/propostas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Proposta> partialUpdateProposta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Proposta proposta
    ) throws URISyntaxException {
        log.debug("REST request to partial update Proposta partially : {}, {}", id, proposta);
        if (proposta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proposta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!propostaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Proposta> result = propostaService.partialUpdate(proposta);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, proposta.getId().toString())
        );
    }

    /**
     * {@code GET  /propostas} : get all the propostas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propostas in body.
     */
    @GetMapping("/propostas")
    public ResponseEntity<List<Proposta>> getAllPropostas(Pageable pageable) {
        log.debug("REST request to get a page of Propostas");
        Page<Proposta> page = propostaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /propostas/:id} : get the "id" proposta.
     *
     * @param id the id of the proposta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proposta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/propostas/{id}")
    public ResponseEntity<Proposta> getProposta(@PathVariable Long id) {
        log.debug("REST request to get Proposta : {}", id);
        Optional<Proposta> proposta = propostaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proposta);
    }

    /**
     * {@code DELETE  /propostas/:id} : delete the "id" proposta.
     *
     * @param id the id of the proposta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/propostas/{id}")
    public ResponseEntity<Void> deleteProposta(@PathVariable Long id) {
        log.debug("REST request to delete Proposta : {}", id);
        propostaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
