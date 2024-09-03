package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CitymasterAsserts.*;
import static com.mycompany.myapp.domain.CitymasterTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CitymasterMapperTest {

    private CitymasterMapper citymasterMapper;

    @BeforeEach
    void setUp() {
        citymasterMapper = new CitymasterMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCitymasterSample1();
        var actual = citymasterMapper.toEntity(citymasterMapper.toDto(expected));
        assertCitymasterAllPropertiesEquals(expected, actual);
    }
}
