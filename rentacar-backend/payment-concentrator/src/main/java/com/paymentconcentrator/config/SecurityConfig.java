package com.paymentconcentrator.config;

import com.paymentconcentrator.security.AuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
                .authorizeRequests()
                //TODO: Ako je nesto public dodati ovde
                .antMatchers("/helloAll/**").permitAll()
                .antMatchers("/search/**").permitAll()
                .antMatchers("/paypal/**").permitAll()
                .antMatchers("/transaction/**").permitAll()
                .antMatchers("/transaction").permitAll()
                .antMatchers("/merchant").permitAll()
                .antMatchers("/merchant/**").permitAll()
                .antMatchers("/request/**").permitAll()
                .anyRequest().authenticated().and()
                .cors().and()
                .addFilterBefore(new AuthenticationFilter(), BasicAuthenticationFilter.class);
    }

}