package br.com.fiap.easyinsurance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.easyinsurance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeguradoraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seguradora.class);
        Seguradora seguradora1 = new Seguradora();
        seguradora1.setId(1L);
        Seguradora seguradora2 = new Seguradora();
        seguradora2.setId(seguradora1.getId());
        assertThat(seguradora1).isEqualTo(seguradora2);
        seguradora2.setId(2L);
        assertThat(seguradora1).isNotEqualTo(seguradora2);
        seguradora1.setId(null);
        assertThat(seguradora1).isNotEqualTo(seguradora2);
    }
}
