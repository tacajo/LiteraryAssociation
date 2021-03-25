package com.authService.security;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class TimeProvider implements Serializable {

    private static final long serialVersionUID = 5363903422953808388L;

    public Date now() {
        return new Date();
    }
}
