package com.example.concecionaria.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(
                        auth -> auth

                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/graphiql").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v1/vehicles/**").hasAnyRole("USER", "ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/v1/vehicles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/vehicles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/vehicles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/vehicles/**").hasRole("ADMIN")

                                .requestMatchers("/api/v1/users/**").hasRole("ADMIN")

                                .requestMatchers("/graphql").authenticated()
                                .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .logout(logout -> logout.logoutUrl("/signout").permitAll());

        return http.build();
    }

}
