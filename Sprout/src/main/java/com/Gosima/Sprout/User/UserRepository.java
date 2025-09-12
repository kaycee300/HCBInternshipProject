package com.Gosima.Sprout.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    static void deleteByEmail(String email) {
    }
}

