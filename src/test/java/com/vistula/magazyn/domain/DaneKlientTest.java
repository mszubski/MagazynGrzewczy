package com.vistula.magazyn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vistula.magazyn.web.rest.TestUtil;

public class DaneKlientTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DaneKlient.class);
        DaneKlient daneKlient1 = new DaneKlient();
        daneKlient1.setId(1L);
        DaneKlient daneKlient2 = new DaneKlient();
        daneKlient2.setId(daneKlient1.getId());
        assertThat(daneKlient1).isEqualTo(daneKlient2);
        daneKlient2.setId(2L);
        assertThat(daneKlient1).isNotEqualTo(daneKlient2);
        daneKlient1.setId(null);
        assertThat(daneKlient1).isNotEqualTo(daneKlient2);
    }
}
