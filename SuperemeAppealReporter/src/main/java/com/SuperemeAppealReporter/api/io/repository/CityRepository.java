package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer>{
	
	@Query(value = "select * from city where state_entity_id = ?1",nativeQuery = true)
	public List<CityEntity> getCityListByStateId(int stateId);
}
