package com.authService.converter;

import com.authService.dto.UserDTO;
import com.authService.model.User;
import org.springframework.core.convert.converter.Converter;

public class UserToUserDTOConverter implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User user) {
        return new UserDTO().builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .build();
    }
}
