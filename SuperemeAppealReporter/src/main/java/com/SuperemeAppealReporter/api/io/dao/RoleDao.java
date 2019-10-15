package com.SuperemeAppealReporter.api.io.dao;

import java.util.ArrayList;

import com.SuperemeAppealReporter.api.io.entity.RoleEntity;

public interface RoleDao {

	public ArrayList<RoleEntity> getAllRoleEntity();
	public RoleEntity getRoleEntityByRoleName(String roleName);
	public RoleEntity getRoleEntityByRoleId(int roleId);
}
