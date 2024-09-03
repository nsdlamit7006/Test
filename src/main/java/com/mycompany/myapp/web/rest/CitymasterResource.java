package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Citymaster;
import com.mycompany.myapp.repository.CitymasterRepository;
import com.mycompany.myapp.service.CitymasterService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Citymaster}.
 */
@RestController
@RequestMapping("/api")
public class CitymasterResource {

    private static final Logger log = LoggerFactory.getLogger(CitymasterResource.class);

    private static final String ENTITY_NAME = "citymaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitymasterService citymasterService;

    private final CitymasterRepository citymasterRepository;

    public CitymasterResource(CitymasterService citymasterService, CitymasterRepository citymasterRepository) {
        this.citymasterService = citymasterService;
        this.citymasterRepository = citymasterRepository;
    }

    /**
     * {@code POST  /citymasters} : Create a new citymaster.
     *
     * @param cityMaster the CityMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new CityMaster, or with status {@code 400 (Bad Request)} if the citymaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/city-master")
    public ResponseEntity<Citymaster> createCitymaster(@RequestBody Citymaster cityMaster) throws URISyntaxException {
        log.debug("REST request to save Citymaster : {}", cityMaster);
        if (cityMaster.getId() != null) {
            throw new BadRequestAlertException("A new citymaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Citymaster result = citymasterService.save(cityMaster);
        return ResponseEntity.created(new URI("/api/citymasters/" + cityMaster.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cityMaster.getId().toString()))
            .body(cityMaster);
    }

    /**
     * {@code PUT  /citymasters/:id} : Updates an existing citymaster.
     *
     * @param id the id of the CityMaster to save.
     * @param CityMaster the CityMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated CityMaster,
     * or with status {@code 400 (Bad Request)} if the CityMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the CityMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/city-master/{id}")
    public ResponseEntity<Citymaster> updateCitymaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Citymaster CityMaster
    ) throws URISyntaxException {
        log.debug("REST request to update Citymaster : {}, {}", id, CityMaster);
        if (CityMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, CityMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citymasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Citymaster result = citymasterService.update(CityMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, CityMaster.getId().toString()))
            .body(CityMaster);
    }

    /**
     * {@code PATCH  /citymasters/:id} : Partial updates given fields of an existing citymaster, field will ignore if it is null
     *
     * @param id the id of the CityMaster to save.
     * @param CityMaster the CityMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated CityMaster,
     * or with status {@code 400 (Bad Request)} if the CityMaster is not valid,
     * or with status {@code 404 (Not Found)} if the CityMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the CityMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/city-master/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Citymaster> partialUpdateCitymaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Citymaster CityMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update Citymaster partially : {}, {}", id, CityMaster);
        if (CityMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, CityMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citymasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Citymaster> result = citymasterService.partialUpdate(CityMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, CityMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /citymasters} : get all the citymasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citymasters in body.
     */
    @GetMapping("/city-master")
    public ResponseEntity<List<Citymaster>> getAllCitymasters(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Citymasters");
        Page<Citymaster> page = citymasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /citymasters/:id} : get the "id" citymaster.
     *
     * @param id the id of the CityMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CityMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/city-master/{id}")
    public ResponseEntity<Citymaster> getCitymaster(@PathVariable("id") Long id) {
        log.debug("REST request to get Citymaster : {}", id);
        Optional<Citymaster> CityMaster = citymasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(CityMaster);
    }

    /**
     * {@code DELETE  /citymasters/:id} : delete the "id" citymaster.
     *
     * @param id the id of the CityMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/city-master/{id}")
    public ResponseEntity<Void> deleteCitymaster(@PathVariable("id") Long id) {
        log.debug("REST request to delete Citymaster : {}", id);
        citymasterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
