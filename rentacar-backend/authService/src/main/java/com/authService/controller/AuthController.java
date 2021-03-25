package com.authService.controller;

import com.authService.dto.UserDTO;
import com.authService.model.TokenValidationResponse;
import com.authService.model.User;
import com.authService.model.UserTokenState;
import com.authService.repository.RoleRepository;
import com.authService.security.TokenUtils;
import com.authService.security.auth.JwtAuthenticationRequest;
import com.authService.service.AuthService;
import com.authService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = {" http://localhost:4200", "http://localhost:4201"})
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        User user = userService.findByUsername(authenticationRequest.getUsername());
        if (user != null) {

            if (BCrypt.checkpw(authenticationRequest.getPassword(), user.getPassword())) {
                logger.info(String.format("Successful login! Username: '%s'.", authenticationRequest.getUsername()));
            } else {
                logger.error(String.format("Incorrect password! Username: '%s'.", authenticationRequest.getUsername()));
                return new ResponseEntity<>("Incorrect password!", HttpStatus.BAD_REQUEST);

            }

            if (!user.isEnabled()) {
                return new ResponseEntity<>("Not activated", HttpStatus.BAD_REQUEST);
            }

            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        } else {
            logger.error(String.format("No user found with username '%s'.", authenticationRequest.getUsername()));
            return new ResponseEntity<>("No user found with this username", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userRequest) {

        logger.info("Registration start...");
        User existUser = this.userService.findByUsername(userRequest.getUsername());
        if (existUser != null) {
            logger.error(String.format("Username already exists '%s'.", userRequest.getUsername()));
            return new ResponseEntity("Username already exists", HttpStatus.FORBIDDEN);
        }
        User user = conversionService.convert(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEnabled(true);
        /*List<Role> roles = new ArrayList<>();
        Optional<Role> role = roleRepository.findById(1L);
        roles.add(role.get());
        user.setRoles(roles);*/
        logger.info("Successful registration!");
        return new ResponseEntity<>(this.userService.save(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/validate-token")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(authService.validateToken(token));
    }

}
