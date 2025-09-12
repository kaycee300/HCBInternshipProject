package com.Gosima.Sprout.User;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account =repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(account.getEmail())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }
}
