package com.Gosima.Sprout.User;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account =repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Role role = Role.valueOf(account.getRole());


        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.addAll(role.getAuthorities());


        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));



        // Build Spring Security user with both role and permissions
        return org.springframework.security.core.userdetails.User
                .withUsername(account.getEmail())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }
}
