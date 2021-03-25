package com.authService.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class UserResponseDTO {

    private Long id;

    private  String firstName;

    private String lastName;

}
