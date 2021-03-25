package com.authService.service;

import com.authService.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    User findByUsername(String username);

    User save(User user);

    void delete(Long id);

    User enableUser(Long id);

    User getUser(String username);

    List<User> getSimpleUser();
}
