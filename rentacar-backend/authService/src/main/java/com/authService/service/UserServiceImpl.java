package com.authService.service;

import com.authService.model.Role;
import com.authService.model.User;
import com.authService.repository.RoleRepository;
import com.authService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            role.getUsers().remove(user);
            roleRepository.save(role);
        }
        userRepository.delete(user);
    }

    public User enableUser(Long id) {
        User user = userRepository.findById(id).get();
        user.setEnabled(!user.isEnabled());
        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getSimpleUser() {
        List<User> users = userRepository.findAll();
        List<User> simpleUsers = new ArrayList<>();
        Role role = roleRepository.findById(1L).get();
        for (User user : users) {
            if(user.getRoles().contains(role)) {
                simpleUsers.add(user);
            }
        }
        return simpleUsers;
    }
}
