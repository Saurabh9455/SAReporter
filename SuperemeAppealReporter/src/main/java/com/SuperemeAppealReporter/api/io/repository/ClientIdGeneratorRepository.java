package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.ClientIdGenerator;

@Repository
public interface ClientIdGeneratorRepository extends PagingAndSortingRepository<ClientIdGenerator, Integer>{

	@Query(value = "select max(id) from client_id_generator",nativeQuery = true)
	public int giveLastClientId();
}
