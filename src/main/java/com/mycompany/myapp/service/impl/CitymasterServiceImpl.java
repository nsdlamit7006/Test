package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Citymaster;
import com.mycompany.myapp.repository.CitymasterRepository;
import com.mycompany.myapp.service.CitymasterService;
import com.mycompany.myapp.service.dto.CitymasterDTO;
import com.mycompany.myapp.service.mapper.CitymasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Citymaster}.
 */
@Service
@Transactional
public class CitymasterServiceImpl implements CitymasterService {

    private static final Logger log = LoggerFactory.getLogger(CitymasterServiceImpl.class);

    private final CitymasterRepository citymasterRepository;

    public CitymasterServiceImpl(CitymasterRepository citymasterRepository) {
        this.citymasterRepository = citymasterRepository;
    }

    @Override
    public Citymaster save(Citymaster citymaster) {
        log.debug("Request to save Citymaster : {}", citymaster);
        return citymasterRepository.save(citymaster);
    }

    @Override
    public Citymaster update(Citymaster citymaster) {
        log.debug("Request to update Citymaster : {}", citymaster);
        return citymasterRepository.save(citymaster);
    }

    @Override
    public Optional<Citymaster> partialUpdate(Citymaster citymaster) {
        log.debug("Request to partially update Citymaster : {}", citymaster);

        return citymasterRepository
            .findById(citymaster.getId())
            .map(existingCitymaster -> {
                if (citymaster.getName() != null) {
                    existingCitymaster.setName(citymaster.getName());
                }

                return existingCitymaster;
            })
            .map(citymasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Citymaster> findAll(Pageable pageable) {
        log.debug("Request to get all Citymasters");
        return citymasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Citymaster> findOne(Long id) {
        log.debug("Request to get Citymaster : {}", id);
        return citymasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Citymaster : {}", id);
        citymasterRepository.deleteById(id);
    }
}
