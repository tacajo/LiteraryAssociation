package com.authService.controller;

import com.authService.dto.UserDTO;
import com.authService.dto.UserResponseDTO;
import com.authService.model.User;
import com.authService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping(value = "/asd")
    @PreAuthorize("hasRole('USER')")
    public void getUserd() {
        System.out.println("usao");
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('USER')")
    public User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void enabledUser(@PathVariable Long id) {
        userService.enableUser(id);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity getUserDB(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping(value = "/get/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        Optional<User> u = userService.findById(id);
        return new UserResponseDTO(u.get().getId(), u.get().getFirstName(), u.get().getLastName());
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        List<User> users = userService.getSimpleUser();
        List<UserDTO> ret = new ArrayList<>();
        for (User user : users) {
            ret.add(conversionService.convert(user, UserDTO.class));
        }
        return ret;
    }

//    @GetMapping(value = "/find/{id}")
//    public UserDTO getUser(@PathVariable Long id) {
//        User user = userService.findById(id).get();
//        return UserDTO.builder()
//                .id(user.getId())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .build();
//    }


}
