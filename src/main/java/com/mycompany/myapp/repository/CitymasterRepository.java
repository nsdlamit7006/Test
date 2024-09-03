package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Citymaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Citymaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitymasterRepository extends JpaRepository<Citymaster, Long> {}
