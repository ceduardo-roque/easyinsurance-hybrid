package br.com.fiap.easyinsurance.web.rest;

import br.com.fiap.easyinsurance.domain.Corretor;
import br.com.fiap.easyinsurance.repository.CorretorRepository;
import br.com.fiap.easyinsurance.service.CorretorService;
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
 * REST controller for managing {@link br.com.fiap.easyinsurance.domain.Corretor}.
 */
@RestController
@RequestMapping("/api")
public class CorretorResource {

    private final Logger log = LoggerFactory.getLogger(CorretorResource.class);

    private static final String ENTITY_NAME = "corretor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorretorService corretorService;

    private final CorretorRepository corretorRepository;

    public CorretorResource(CorretorService corretorService, CorretorRepository corretorRepository) {
        this.corretorService = corretorService;
        this.corretorRepository = corretorRepository;
    }

    /**
     * {@code POST  /corretors} : Create a new corretor.
     *
     * @param corretor the corretor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new corretor, or with status {@code 400 (Bad Request)} if the corretor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/corretors")
    public ResponseEntity<Corretor> createCorretor(@Valid @RequestBody Corretor corretor) throws URISyntaxException {
        log.debug("REST request to save Corretor : {}", corretor);
        if (corretor.getId() != null) {
            throw new BadRequestAlertException("A new corretor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Corretor result = corretorService.save(corretor);
        return ResponseEntity
            .created(new URI("/api/corretors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /corretors/:id} : Updates an existing corretor.
     *
     * @param id the id of the corretor to save.
     * @param corretor the corretor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated corretor,
     * or with status {@code 400 (Bad Request)} if the corretor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the corretor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/corretors/{id}")
    public ResponseEntity<Corretor> updateCorretor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Corretor corretor
    ) throws URISyntaxException {
        log.debug("REST request to update Corretor : {}, {}", id, corretor);
        if (corretor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, corretor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!corretorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Corretor result = corretorService.save(corretor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, corretor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /corretors/:id} : Partial updates given fields of an existing corretor, field will ignore if it is null
     *
     * @param id the id of the corretor to save.
     * @param corretor the corretor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated corretor,
     * or with status {@code 400 (Bad Request)} if the corretor is not valid,
     * or with status {@code 404 (Not Found)} if the corretor is not found,
     * or with status {@code 500 (Internal Server Error)} if the corretor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/corretors/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Corretor> partialUpdateCorretor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Corretor corretor
    ) throws URISyntaxException {
        log.debug("REST request to partial update Corretor partially : {}, {}", id, corretor);
        if (corretor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, corretor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!corretorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Corretor> result = corretorService.partialUpdate(corretor);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, corretor.getId().toString())
        );
    }

    /**
     * {@code GET  /corretors} : get all the corretors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of corretors in body.
     */
    @GetMapping("/corretors")
    public ResponseEntity<List<Corretor>> getAllCorretors(Pageable pageable) {
        log.debug("REST request to get a page of Corretors");
        Page<Corretor> page = corretorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /corretors/:id} : get the "id" corretor.
     *
     * @param id the id of the corretor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the corretor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/corretors/{id}")
    public ResponseEntity<Corretor> getCorretor(@PathVariable Long id) {
        log.debug("REST request to get Corretor : {}", id);
        Optional<Corretor> corretor = corretorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(corretor);
    }

    /**
     * {@code DELETE  /corretors/:id} : delete the "id" corretor.
     *
     * @param id the id of the corretor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/corretors/{id}")
    public ResponseEntity<Void> deleteCorretor(@PathVariable Long id) {
        log.debug("REST request to delete Corretor : {}", id);
        corretorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
