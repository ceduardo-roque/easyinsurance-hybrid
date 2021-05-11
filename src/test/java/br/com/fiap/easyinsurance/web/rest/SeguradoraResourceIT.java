package br.com.fiap.easyinsurance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.fiap.easyinsurance.IntegrationTest;
import br.com.fiap.easyinsurance.domain.Seguradora;
import br.com.fiap.easyinsurance.repository.SeguradoraRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SeguradoraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeguradoraResourceIT {

    private static final String DEFAULT_NOME_SEGURADORA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_SEGURADORA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/seguradoras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeguradoraRepository seguradoraRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeguradoraMockMvc;

    private Seguradora seguradora;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seguradora createEntity(EntityManager em) {
        Seguradora seguradora = new Seguradora().nomeSeguradora(DEFAULT_NOME_SEGURADORA);
        return seguradora;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seguradora createUpdatedEntity(EntityManager em) {
        Seguradora seguradora = new Seguradora().nomeSeguradora(UPDATED_NOME_SEGURADORA);
        return seguradora;
    }

    @BeforeEach
    public void initTest() {
        seguradora = createEntity(em);
    }

    @Test
    @Transactional
    void createSeguradora() throws Exception {
        int databaseSizeBeforeCreate = seguradoraRepository.findAll().size();
        // Create the Seguradora
        restSeguradoraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seguradora)))
            .andExpect(status().isCreated());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeCreate + 1);
        Seguradora testSeguradora = seguradoraList.get(seguradoraList.size() - 1);
        assertThat(testSeguradora.getNomeSeguradora()).isEqualTo(DEFAULT_NOME_SEGURADORA);
    }

    @Test
    @Transactional
    void createSeguradoraWithExistingId() throws Exception {
        // Create the Seguradora with an existing ID
        seguradora.setId(1L);

        int databaseSizeBeforeCreate = seguradoraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeguradoraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seguradora)))
            .andExpect(status().isBadRequest());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeSeguradoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = seguradoraRepository.findAll().size();
        // set the field null
        seguradora.setNomeSeguradora(null);

        // Create the Seguradora, which fails.

        restSeguradoraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seguradora)))
            .andExpect(status().isBadRequest());

        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSeguradoras() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        // Get all the seguradoraList
        restSeguradoraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seguradora.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeSeguradora").value(hasItem(DEFAULT_NOME_SEGURADORA)));
    }

    @Test
    @Transactional
    void getSeguradora() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        // Get the seguradora
        restSeguradoraMockMvc
            .perform(get(ENTITY_API_URL_ID, seguradora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seguradora.getId().intValue()))
            .andExpect(jsonPath("$.nomeSeguradora").value(DEFAULT_NOME_SEGURADORA));
    }

    @Test
    @Transactional
    void getNonExistingSeguradora() throws Exception {
        // Get the seguradora
        restSeguradoraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeguradora() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();

        // Update the seguradora
        Seguradora updatedSeguradora = seguradoraRepository.findById(seguradora.getId()).get();
        // Disconnect from session so that the updates on updatedSeguradora are not directly saved in db
        em.detach(updatedSeguradora);
        updatedSeguradora.nomeSeguradora(UPDATED_NOME_SEGURADORA);

        restSeguradoraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSeguradora.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSeguradora))
            )
            .andExpect(status().isOk());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
        Seguradora testSeguradora = seguradoraList.get(seguradoraList.size() - 1);
        assertThat(testSeguradora.getNomeSeguradora()).isEqualTo(UPDATED_NOME_SEGURADORA);
    }

    @Test
    @Transactional
    void putNonExistingSeguradora() throws Exception {
        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();
        seguradora.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeguradoraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seguradora.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seguradora))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeguradora() throws Exception {
        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();
        seguradora.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguradoraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seguradora))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeguradora() throws Exception {
        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();
        seguradora.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguradoraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seguradora)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeguradoraWithPatch() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();

        // Update the seguradora using partial update
        Seguradora partialUpdatedSeguradora = new Seguradora();
        partialUpdatedSeguradora.setId(seguradora.getId());

        restSeguradoraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeguradora.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeguradora))
            )
            .andExpect(status().isOk());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
        Seguradora testSeguradora = seguradoraList.get(seguradoraList.size() - 1);
        assertThat(testSeguradora.getNomeSeguradora()).isEqualTo(DEFAULT_NOME_SEGURADORA);
    }

    @Test
    @Transactional
    void fullUpdateSeguradoraWithPatch() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();

        // Update the seguradora using partial update
        Seguradora partialUpdatedSeguradora = new Seguradora();
        partialUpdatedSeguradora.setId(seguradora.getId());

        partialUpdatedSeguradora.nomeSeguradora(UPDATED_NOME_SEGURADORA);

        restSeguradoraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeguradora.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeguradora))
            )
            .andExpect(status().isOk());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
        Seguradora testSeguradora = seguradoraList.get(seguradoraList.size() - 1);
        assertThat(testSeguradora.getNomeSeguradora()).isEqualTo(UPDATED_NOME_SEGURADORA);
    }

    @Test
    @Transactional
    void patchNonExistingSeguradora() throws Exception {
        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();
        seguradora.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeguradoraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seguradora.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seguradora))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeguradora() throws Exception {
        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();
        seguradora.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguradoraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seguradora))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeguradora() throws Exception {
        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();
        seguradora.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguradoraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seguradora))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeguradora() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        int databaseSizeBeforeDelete = seguradoraRepository.findAll().size();

        // Delete the seguradora
        restSeguradoraMockMvc
            .perform(delete(ENTITY_API_URL_ID, seguradora.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
