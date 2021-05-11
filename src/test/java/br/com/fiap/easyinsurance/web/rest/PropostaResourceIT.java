package br.com.fiap.easyinsurance.web.rest;

import static br.com.fiap.easyinsurance.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.fiap.easyinsurance.IntegrationTest;
import br.com.fiap.easyinsurance.domain.Proposta;
import br.com.fiap.easyinsurance.domain.enumeration.Status;
import br.com.fiap.easyinsurance.repository.PropostaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PropostaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PropostaResourceIT {

    private static final String DEFAULT_NUMERO_PROPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROPOSTA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_PROPOSTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PROPOSTA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VALOR_PROPOSTA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_PROPOSTA = new BigDecimal(2);

    private static final Status DEFAULT_STATUS = Status.ATIVO;
    private static final Status UPDATED_STATUS = Status.CANCELADO;

    private static final String ENTITY_API_URL = "/api/propostas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPropostaMockMvc;

    private Proposta proposta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proposta createEntity(EntityManager em) {
        Proposta proposta = new Proposta()
            .numeroProposta(DEFAULT_NUMERO_PROPOSTA)
            .dataProposta(DEFAULT_DATA_PROPOSTA)
            .valorProposta(DEFAULT_VALOR_PROPOSTA)
            .status(DEFAULT_STATUS);
        return proposta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proposta createUpdatedEntity(EntityManager em) {
        Proposta proposta = new Proposta()
            .numeroProposta(UPDATED_NUMERO_PROPOSTA)
            .dataProposta(UPDATED_DATA_PROPOSTA)
            .valorProposta(UPDATED_VALOR_PROPOSTA)
            .status(UPDATED_STATUS);
        return proposta;
    }

    @BeforeEach
    public void initTest() {
        proposta = createEntity(em);
    }

    @Test
    @Transactional
    void createProposta() throws Exception {
        int databaseSizeBeforeCreate = propostaRepository.findAll().size();
        // Create the Proposta
        restPropostaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proposta)))
            .andExpect(status().isCreated());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeCreate + 1);
        Proposta testProposta = propostaList.get(propostaList.size() - 1);
        assertThat(testProposta.getNumeroProposta()).isEqualTo(DEFAULT_NUMERO_PROPOSTA);
        assertThat(testProposta.getDataProposta()).isEqualTo(DEFAULT_DATA_PROPOSTA);
        assertThat(testProposta.getValorProposta()).isEqualByComparingTo(DEFAULT_VALOR_PROPOSTA);
        assertThat(testProposta.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createPropostaWithExistingId() throws Exception {
        // Create the Proposta with an existing ID
        proposta.setId(1L);

        int databaseSizeBeforeCreate = propostaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropostaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proposta)))
            .andExpect(status().isBadRequest());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroPropostaIsRequired() throws Exception {
        int databaseSizeBeforeTest = propostaRepository.findAll().size();
        // set the field null
        proposta.setNumeroProposta(null);

        // Create the Proposta, which fails.

        restPropostaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proposta)))
            .andExpect(status().isBadRequest());

        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataPropostaIsRequired() throws Exception {
        int databaseSizeBeforeTest = propostaRepository.findAll().size();
        // set the field null
        proposta.setDataProposta(null);

        // Create the Proposta, which fails.

        restPropostaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proposta)))
            .andExpect(status().isBadRequest());

        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorPropostaIsRequired() throws Exception {
        int databaseSizeBeforeTest = propostaRepository.findAll().size();
        // set the field null
        proposta.setValorProposta(null);

        // Create the Proposta, which fails.

        restPropostaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proposta)))
            .andExpect(status().isBadRequest());

        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = propostaRepository.findAll().size();
        // set the field null
        proposta.setStatus(null);

        // Create the Proposta, which fails.

        restPropostaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proposta)))
            .andExpect(status().isBadRequest());

        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPropostas() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        // Get all the propostaList
        restPropostaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proposta.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroProposta").value(hasItem(DEFAULT_NUMERO_PROPOSTA)))
            .andExpect(jsonPath("$.[*].dataProposta").value(hasItem(DEFAULT_DATA_PROPOSTA.toString())))
            .andExpect(jsonPath("$.[*].valorProposta").value(hasItem(sameNumber(DEFAULT_VALOR_PROPOSTA))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getProposta() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        // Get the proposta
        restPropostaMockMvc
            .perform(get(ENTITY_API_URL_ID, proposta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proposta.getId().intValue()))
            .andExpect(jsonPath("$.numeroProposta").value(DEFAULT_NUMERO_PROPOSTA))
            .andExpect(jsonPath("$.dataProposta").value(DEFAULT_DATA_PROPOSTA.toString()))
            .andExpect(jsonPath("$.valorProposta").value(sameNumber(DEFAULT_VALOR_PROPOSTA)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProposta() throws Exception {
        // Get the proposta
        restPropostaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProposta() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();

        // Update the proposta
        Proposta updatedProposta = propostaRepository.findById(proposta.getId()).get();
        // Disconnect from session so that the updates on updatedProposta are not directly saved in db
        em.detach(updatedProposta);
        updatedProposta
            .numeroProposta(UPDATED_NUMERO_PROPOSTA)
            .dataProposta(UPDATED_DATA_PROPOSTA)
            .valorProposta(UPDATED_VALOR_PROPOSTA)
            .status(UPDATED_STATUS);

        restPropostaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProposta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProposta))
            )
            .andExpect(status().isOk());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
        Proposta testProposta = propostaList.get(propostaList.size() - 1);
        assertThat(testProposta.getNumeroProposta()).isEqualTo(UPDATED_NUMERO_PROPOSTA);
        assertThat(testProposta.getDataProposta()).isEqualTo(UPDATED_DATA_PROPOSTA);
        assertThat(testProposta.getValorProposta()).isEqualTo(UPDATED_VALOR_PROPOSTA);
        assertThat(testProposta.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingProposta() throws Exception {
        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();
        proposta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropostaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proposta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proposta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProposta() throws Exception {
        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();
        proposta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropostaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proposta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProposta() throws Exception {
        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();
        proposta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropostaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proposta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePropostaWithPatch() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();

        // Update the proposta using partial update
        Proposta partialUpdatedProposta = new Proposta();
        partialUpdatedProposta.setId(proposta.getId());

        partialUpdatedProposta.numeroProposta(UPDATED_NUMERO_PROPOSTA).dataProposta(UPDATED_DATA_PROPOSTA);

        restPropostaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProposta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProposta))
            )
            .andExpect(status().isOk());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
        Proposta testProposta = propostaList.get(propostaList.size() - 1);
        assertThat(testProposta.getNumeroProposta()).isEqualTo(UPDATED_NUMERO_PROPOSTA);
        assertThat(testProposta.getDataProposta()).isEqualTo(UPDATED_DATA_PROPOSTA);
        assertThat(testProposta.getValorProposta()).isEqualByComparingTo(DEFAULT_VALOR_PROPOSTA);
        assertThat(testProposta.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePropostaWithPatch() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();

        // Update the proposta using partial update
        Proposta partialUpdatedProposta = new Proposta();
        partialUpdatedProposta.setId(proposta.getId());

        partialUpdatedProposta
            .numeroProposta(UPDATED_NUMERO_PROPOSTA)
            .dataProposta(UPDATED_DATA_PROPOSTA)
            .valorProposta(UPDATED_VALOR_PROPOSTA)
            .status(UPDATED_STATUS);

        restPropostaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProposta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProposta))
            )
            .andExpect(status().isOk());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
        Proposta testProposta = propostaList.get(propostaList.size() - 1);
        assertThat(testProposta.getNumeroProposta()).isEqualTo(UPDATED_NUMERO_PROPOSTA);
        assertThat(testProposta.getDataProposta()).isEqualTo(UPDATED_DATA_PROPOSTA);
        assertThat(testProposta.getValorProposta()).isEqualByComparingTo(UPDATED_VALOR_PROPOSTA);
        assertThat(testProposta.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingProposta() throws Exception {
        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();
        proposta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropostaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proposta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proposta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProposta() throws Exception {
        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();
        proposta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropostaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proposta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProposta() throws Exception {
        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();
        proposta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropostaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(proposta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proposta in the database
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProposta() throws Exception {
        // Initialize the database
        propostaRepository.saveAndFlush(proposta);

        int databaseSizeBeforeDelete = propostaRepository.findAll().size();

        // Delete the proposta
        restPropostaMockMvc
            .perform(delete(ENTITY_API_URL_ID, proposta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proposta> propostaList = propostaRepository.findAll();
        assertThat(propostaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
