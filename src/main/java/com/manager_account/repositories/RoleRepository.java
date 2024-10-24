package com.manager_account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manager_account.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	public Role findByName(String name);
}