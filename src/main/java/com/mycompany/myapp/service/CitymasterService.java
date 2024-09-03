package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Citymaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Citymaster}.
 */
public interface CitymasterService {
    /**
     * Save a citymaster.
     *
     * @param citymaster the entity to save.
     * @return the persisted entity.
     */
    Citymaster save(Citymaster citymaster);

    /**
     * Updates a citymaster.
     *
     * @param citymaster the entity to update.
     * @return the persisted entity.
     */
    Citymaster update(Citymaster citymaster);

    /**
     * Partially updates a citymaster.
     *
     * @param citymaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Citymaster> partialUpdate(Citymaster citymaster);

    /**
     * Get all the citymasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Citymaster> findAll(Pageable pageable);

    /**
     * Get the "id" citymaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Citymaster> findOne(Long id);

    /**
     * Delete the "id" citymaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
