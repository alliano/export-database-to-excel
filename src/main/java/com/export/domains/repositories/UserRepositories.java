package com.export.domains.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.export.domains.entities.User;
import com.export.dtos.UserDetailDto;

public interface UserRepositories extends JpaRepository<User, Long> {

    @Query(nativeQuery = false, name = "query_projection_user_detail",
    value = "SELECT DISTINCT "
    + "new com.export.dtos.UserDetailDto(u.secureId, u.name, u.email, u.password, r.name, r.description) "
    + "FROM User AS u INNER JOIN u.role as u_r "
    + "INNER JOIN Role AS r ON (u_r.id = r.id)")
    public List<UserDetailDto> findAllUserDetail();

}
