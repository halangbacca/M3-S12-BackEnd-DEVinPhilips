package com.medsoft.labmedial.config;

import com.medsoft.labmedial.security.JWTFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;
    private final JWTFilter jwtFilter;
    public SpringSecurityConfig(AuthenticationEntryPoint authEntryPoint, JWTFilter jwtFilter) {
        this.authEntryPoint = authEntryPoint;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                .csrf( csrf -> csrf.disable())
                .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement( sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(aut-> aut
                        .requestMatchers(HttpMethod.POST,"/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/usuarios/login").permitAll()
                        .requestMatchers("/**").authenticated()
                )
                .cors(cors-> new CorsConfiguration())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(this.authEntryPoint))
        ;

        return  http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

}
