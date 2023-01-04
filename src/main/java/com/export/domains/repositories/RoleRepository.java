package com.export.domains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.export.domains.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
