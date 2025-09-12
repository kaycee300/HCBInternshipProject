package com.Gosima.Sprout.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




public interface UserService {

    Account CreateUser(Account account);

    List<Account> getUsers();

    Optional<Account> getUserByEmail(String email);

    Account updateUser(String email, Account account);

    void deleteUser(String email);
}
