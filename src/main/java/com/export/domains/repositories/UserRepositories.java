package com.export.domains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.export.domains.entities.User;

public interface UserRepositories extends JpaRepository<User, Long> {

}
