package com.bk.progresstracker;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RegisteredDomainRepo extends CrudRepository<RegisteredDomain, Long> {

	Optional<RegisteredDomain> findByDomainHost(String hostName);

}
