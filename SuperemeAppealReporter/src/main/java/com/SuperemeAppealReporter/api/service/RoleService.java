package com.SuperemeAppealReporter.api.service;

import java.util.ArrayList;

import com.SuperemeAppealReporter.api.io.entity.RoleEntity;

public interface RoleService {

	public ArrayList<RoleEntity> getAllRoleService();
	public RoleEntity findByRoleNameService(String roleName);
	public RoleEntity findByRoleId(int roleId);
}
