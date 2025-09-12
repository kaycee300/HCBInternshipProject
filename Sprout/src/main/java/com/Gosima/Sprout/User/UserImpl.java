package com.Gosima.Sprout.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service

public class UserImpl implements UserService{

    private final UserRepository userRepository;

    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Account CreateUser(Account account) {
        return userRepository.save(account);
    }

    @Override
    public List<Account> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Account> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Account updateUser(String email, Account account) {
        return userRepository.findByEmail(email)
                .map(existing -> {
                    existing.setName(account.getName());
                    existing.setAddress(account.getAddress());
                    existing.setPhoneNo(account.getPhoneNo());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("user not found with email: " + email));
    }


    @Override
    public void deleteUser(String email) {
        UserRepository.deleteByEmail(email);

    }
}
