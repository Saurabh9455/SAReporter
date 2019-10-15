package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.StateEntity;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Integer> {

	@Query(value = "select * from state where country_entity_id = ?1",nativeQuery = true)
	public List<StateEntity> getStateListByCountryId(int countryId);
}
