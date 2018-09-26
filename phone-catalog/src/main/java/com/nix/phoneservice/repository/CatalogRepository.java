package com.nix.phoneservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CatalogRepository<T> extends JpaRepository<T, Long> {
}