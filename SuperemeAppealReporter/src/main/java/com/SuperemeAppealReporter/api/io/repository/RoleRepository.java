package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.RoleEntity;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Integer> {

	public RoleEntity findByName(String roleName);
	
    public RoleEntity findById(int roleId);
	
}
