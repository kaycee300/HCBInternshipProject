package com.Gosima.Sprout.AuthenticationController;

import com.Gosima.Sprout.JWT.SproutJwtService;
import com.Gosima.Sprout.User.Account;
import com.Gosima.Sprout.User.UserRepository;
import com.Gosima.Sprout.UserRequest.AuthRequest;
import com.Gosima.Sprout.UserRequest.AuthResponse;
import com.Gosima.Sprout.UserRequest.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SproutAuthController {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final SproutJwtService jwtService;

    private final UserRepository userRepository;


    public SproutAuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, SproutJwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        Account account = new Account();

        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setAddress(request.getAddress());
        account.setPhoneNo(request.getPhoneNo());
        account.setRole(request.getRole());
        userRepository.save(account);
        return ResponseEntity.ok("Registered Users Successfully");

    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody
                                              AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        Account account = userRepository.findByEmail(request.getEmail()).get();
        String token = jwtService.generateToken(account);
        return ResponseEntity.ok(new AuthResponse(token));
    }

}


