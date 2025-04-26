package com.spring.blogApp_api.repositories;

import com.spring.blogApp_api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
