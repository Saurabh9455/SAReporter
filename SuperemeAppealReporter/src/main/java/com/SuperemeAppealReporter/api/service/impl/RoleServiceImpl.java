package com.SuperemeAppealReporter.api.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.io.dao.RoleDao;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	
	public ArrayList<RoleEntity> getAllRoleService()
	{
		return roleDao.getAllRoleEntity();
		
	}


	@Override
	public RoleEntity findByRoleNameService(String roleName) {
		return  roleDao.getRoleEntityByRoleName(roleName);
	}


	@Override
	public RoleEntity findByRoleId(int roleId) {
		return roleDao.getRoleEntityByRoleId(roleId);
	}
}
