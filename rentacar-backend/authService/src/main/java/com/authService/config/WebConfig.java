package com.authService.config;

import com.authService.converter.UserDTOToUserConverter;
import com.authService.converter.UserToUserDTOConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserDTOToUserConverter());
        registry.addConverter(new UserToUserDTOConverter());
    }
}
