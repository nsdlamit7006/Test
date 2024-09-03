package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CitymasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitymasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Citymaster.class);
        Citymaster citymaster1 = getCitymasterSample1();
        Citymaster citymaster2 = new Citymaster();
        assertThat(citymaster1).isNotEqualTo(citymaster2);

        citymaster2.setId(citymaster1.getId());
        assertThat(citymaster1).isEqualTo(citymaster2);

        citymaster2 = getCitymasterSample2();
        assertThat(citymaster1).isNotEqualTo(citymaster2);
    }
}
