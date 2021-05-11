package br.com.fiap.easyinsurance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.easyinsurance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PropostaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proposta.class);
        Proposta proposta1 = new Proposta();
        proposta1.setId(1L);
        Proposta proposta2 = new Proposta();
        proposta2.setId(proposta1.getId());
        assertThat(proposta1).isEqualTo(proposta2);
        proposta2.setId(2L);
        assertThat(proposta1).isNotEqualTo(proposta2);
        proposta1.setId(null);
        assertThat(proposta1).isNotEqualTo(proposta2);
    }
}
