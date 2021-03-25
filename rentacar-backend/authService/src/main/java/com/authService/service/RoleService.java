package com.authService.service;

import com.authService.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(Long id);
    List<Role> findByname(String name);
}
