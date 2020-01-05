package com.vistula.magazyn.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vistula.magazyn.web.rest.TestUtil;

public class ZamowienieWpisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZamowienieWpis.class);
        ZamowienieWpis zamowienieWpis1 = new ZamowienieWpis();
        zamowienieWpis1.setId(1L);
        ZamowienieWpis zamowienieWpis2 = new ZamowienieWpis();
        zamowienieWpis2.setId(zamowienieWpis1.getId());
        assertThat(zamowienieWpis1).isEqualTo(zamowienieWpis2);
        zamowienieWpis2.setId(2L);
        assertThat(zamowienieWpis1).isNotEqualTo(zamowienieWpis2);
        zamowienieWpis1.setId(null);
        assertThat(zamowienieWpis1).isNotEqualTo(zamowienieWpis2);
    }
}
