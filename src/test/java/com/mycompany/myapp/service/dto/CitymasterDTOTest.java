package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitymasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitymasterDTO.class);
        CitymasterDTO citymasterDTO1 = new CitymasterDTO();
        citymasterDTO1.setId(1L);
        CitymasterDTO citymasterDTO2 = new CitymasterDTO();
        assertThat(citymasterDTO1).isNotEqualTo(citymasterDTO2);
        citymasterDTO2.setId(citymasterDTO1.getId());
        assertThat(citymasterDTO1).isEqualTo(citymasterDTO2);
        citymasterDTO2.setId(2L);
        assertThat(citymasterDTO1).isNotEqualTo(citymasterDTO2);
        citymasterDTO1.setId(null);
        assertThat(citymasterDTO1).isNotEqualTo(citymasterDTO2);
    }
}
