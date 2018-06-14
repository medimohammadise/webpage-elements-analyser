package com.scout24.techchalenge.webpageanalyserapp.repository;

import com.scout24.techchalenge.webpageanalyserapp.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
