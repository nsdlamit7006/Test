package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Citymaster;
import com.mycompany.myapp.service.dto.CitymasterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Citymaster} and its DTO {@link CitymasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface CitymasterMapper extends EntityMapper<CitymasterDTO, Citymaster> {}
