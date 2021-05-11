package br.com.fiap.easyinsurance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.easyinsurance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CorretorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Corretor.class);
        Corretor corretor1 = new Corretor();
        corretor1.setId(1L);
        Corretor corretor2 = new Corretor();
        corretor2.setId(corretor1.getId());
        assertThat(corretor1).isEqualTo(corretor2);
        corretor2.setId(2L);
        assertThat(corretor1).isNotEqualTo(corretor2);
        corretor1.setId(null);
        assertThat(corretor1).isNotEqualTo(corretor2);
    }
}
