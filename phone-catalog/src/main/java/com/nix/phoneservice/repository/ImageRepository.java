package com.nix.phoneservice.repository;

import com.nix.phoneservice.domain.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends CatalogRepository<ImageEntity> {
    Optional<ImageEntity> findByUuid(String uuid);

    Page<ImageEntity> findAll(Pageable pageable);
}
