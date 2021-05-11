package br.com.fiap.easyinsurance.web.rest;

import br.com.fiap.easyinsurance.domain.Seguradora;
import br.com.fiap.easyinsurance.repository.SeguradoraRepository;
import br.com.fiap.easyinsurance.service.SeguradoraService;
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
 * REST controller for managing {@link br.com.fiap.easyinsurance.domain.Seguradora}.
 */
@RestController
@RequestMapping("/api")
public class SeguradoraResource {

    private final Logger log = LoggerFactory.getLogger(SeguradoraResource.class);

    private static final String ENTITY_NAME = "seguradora";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeguradoraService seguradoraService;

    private final SeguradoraRepository seguradoraRepository;

    public SeguradoraResource(SeguradoraService seguradoraService, SeguradoraRepository seguradoraRepository) {
        this.seguradoraService = seguradoraService;
        this.seguradoraRepository = seguradoraRepository;
    }

    /**
     * {@code POST  /seguradoras} : Create a new seguradora.
     *
     * @param seguradora the seguradora to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seguradora, or with status {@code 400 (Bad Request)} if the seguradora has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seguradoras")
    public ResponseEntity<Seguradora> createSeguradora(@Valid @RequestBody Seguradora seguradora) throws URISyntaxException {
        log.debug("REST request to save Seguradora : {}", seguradora);
        if (seguradora.getId() != null) {
            throw new BadRequestAlertException("A new seguradora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Seguradora result = seguradoraService.save(seguradora);
        return ResponseEntity
            .created(new URI("/api/seguradoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seguradoras/:id} : Updates an existing seguradora.
     *
     * @param id the id of the seguradora to save.
     * @param seguradora the seguradora to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seguradora,
     * or with status {@code 400 (Bad Request)} if the seguradora is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seguradora couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seguradoras/{id}")
    public ResponseEntity<Seguradora> updateSeguradora(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Seguradora seguradora
    ) throws URISyntaxException {
        log.debug("REST request to update Seguradora : {}, {}", id, seguradora);
        if (seguradora.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seguradora.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seguradoraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Seguradora result = seguradoraService.save(seguradora);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, seguradora.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seguradoras/:id} : Partial updates given fields of an existing seguradora, field will ignore if it is null
     *
     * @param id the id of the seguradora to save.
     * @param seguradora the seguradora to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seguradora,
     * or with status {@code 400 (Bad Request)} if the seguradora is not valid,
     * or with status {@code 404 (Not Found)} if the seguradora is not found,
     * or with status {@code 500 (Internal Server Error)} if the seguradora couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seguradoras/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Seguradora> partialUpdateSeguradora(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Seguradora seguradora
    ) throws URISyntaxException {
        log.debug("REST request to partial update Seguradora partially : {}, {}", id, seguradora);
        if (seguradora.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seguradora.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seguradoraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Seguradora> result = seguradoraService.partialUpdate(seguradora);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, seguradora.getId().toString())
        );
    }

    /**
     * {@code GET  /seguradoras} : get all the seguradoras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seguradoras in body.
     */
    @GetMapping("/seguradoras")
    public ResponseEntity<List<Seguradora>> getAllSeguradoras(Pageable pageable) {
        log.debug("REST request to get a page of Seguradoras");
        Page<Seguradora> page = seguradoraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seguradoras/:id} : get the "id" seguradora.
     *
     * @param id the id of the seguradora to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seguradora, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seguradoras/{id}")
    public ResponseEntity<Seguradora> getSeguradora(@PathVariable Long id) {
        log.debug("REST request to get Seguradora : {}", id);
        Optional<Seguradora> seguradora = seguradoraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seguradora);
    }

    /**
     * {@code DELETE  /seguradoras/:id} : delete the "id" seguradora.
     *
     * @param id the id of the seguradora to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seguradoras/{id}")
    public ResponseEntity<Void> deleteSeguradora(@PathVariable Long id) {
        log.debug("REST request to delete Seguradora : {}", id);
        seguradoraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
