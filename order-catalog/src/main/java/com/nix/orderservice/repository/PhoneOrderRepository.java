package com.nix.orderservice.repository;

import com.nix.orderservice.domain.PhoneOrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneOrderRepository extends CatalogRepository<PhoneOrderEntity> {
    @EntityGraph(attributePaths = {"order"})
    Optional<PhoneOrderEntity> findById(Long id);
}