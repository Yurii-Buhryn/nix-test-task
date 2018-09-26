package com.nix.orderservice.repository;

import com.nix.orderservice.domain.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CatalogRepository<OrderEntity> {
    @EntityGraph(attributePaths = {"phones"})
    Optional<OrderEntity> findByUuid(String uuid);

}