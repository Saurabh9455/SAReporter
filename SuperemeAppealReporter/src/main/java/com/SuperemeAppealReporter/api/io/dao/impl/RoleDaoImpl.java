package com.SuperemeAppealReporter.api.io.dao.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.io.dao.RoleDao;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.io.repository.RoleRepository;

@Component
public class RoleDaoImpl implements RoleDao {

	@Autowired
	RoleRepository roleRepository;
	

	@Override
	public ArrayList<RoleEntity> getAllRoleEntity() {
		
		return (ArrayList<RoleEntity>)roleRepository.findAll();
	}

	@Override
	public RoleEntity getRoleEntityByRoleName(String roleName) {
	
		return roleRepository.findByName(roleName);
	}

	@Override
	public RoleEntity getRoleEntityByRoleId(int roleId) {
		
		return roleRepository.findById(roleId);
	}

}
