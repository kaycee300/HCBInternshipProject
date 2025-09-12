package com.Gosima.Sprout.Config;


import com.Gosima.Sprout.JWT.SproutJwtAuthFilter;
import com.Gosima.Sprout.User.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SproutSecurityConfig {

   private final SproutJwtAuthFilter jwtAuthFilter;
    private final MyUserDetailsService userDetailsService;



    public SproutSecurityConfig(SproutJwtAuthFilter jwtAuthFilter, MyUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;

        this.userDetailsService = userDetailsService;
    }


    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
//                .csrf(csrf -> csrf.disable())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login", "/h2-console").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/vendor").hasRole("VENDOR")
                        .requestMatchers("/customer").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }
}