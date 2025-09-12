package com.Gosima.Sprout.User;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public Account create(@RequestBody Account account){
        return userService.CreateUser(account);
    }

    @GetMapping
    public List<Account> findAll(){
        return userService.getUsers();
    }


    @GetMapping("/{email}")
    public Account findByEmail(@PathVariable String email){
        return userService.getUserByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/{email}")
    public Account update(@PathVariable String email, @RequestBody Account account){
        return userService.updateUser(email, account);
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable String email){
        userService.deleteUser(email);
    }



}
