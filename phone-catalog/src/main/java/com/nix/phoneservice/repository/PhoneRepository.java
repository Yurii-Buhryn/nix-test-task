package com.nix.phoneservice.repository;

import com.nix.phoneservice.domain.PhoneEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends CatalogRepository<PhoneEntity> {
    @EntityGraph(attributePaths = {"image"})
    Optional<PhoneEntity> findByUuid(String uuid);

    @EntityGraph(attributePaths = {"image"})
    Page<PhoneEntity> findAll(Pageable pageable);
}
