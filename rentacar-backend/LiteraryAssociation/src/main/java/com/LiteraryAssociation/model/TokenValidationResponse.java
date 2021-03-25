package com.LiteraryAssociation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenValidationResponse {
    private Boolean isValid = false;
    private Long userId;
    private String username;
    private String authorities;
}
