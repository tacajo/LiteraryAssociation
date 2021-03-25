package com.authService.service;

import com.authService.model.TokenValidationResponse;
import com.authService.model.User;
import com.authService.security.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void login() {

    }

    @Override
    public void register() {

    }


    @Override
    public void blockUser() {

    }

    @Override
    public void activateUser() {

    }

    @Override
    public void removeUser() {

    }


    public TokenValidationResponse validateToken(String token) {
        System.out.println("usao u validateToken()");
        // create new response in which token is marked as invalid
        TokenValidationResponse response = new TokenValidationResponse();
        // remove Bearer keyword
        token = tokenUtils.getToken(token);
        if (token != null) {
            // find user linked to token
            String username = tokenUtils.getUsernameFromToken(token);
            if (username != null) {
                // load user and check if token is valid
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                if (tokenUtils.validateToken(token, userDetails)) {
                    User user = (User) userDetails;
                    // set information
                    response.setIsValid(true);
                    response.setUserId(user.getId());
                    response.setUsername(username);
                    List<String> authorities = userDetails.getAuthorities().stream()
                            .map(r -> r.getAuthority()).collect(Collectors.toList());
                    response.setAuthorities(StringUtils.join(authorities, ","));
                }
            }
        }
        return response;
    }
}
