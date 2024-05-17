package com.sergosoft.polymate.controller;

import com.sergosoft.polymate.auth.AuthenticationRequest;
import com.sergosoft.polymate.auth.AuthenticationResponse;
import com.sergosoft.polymate.auth.AuthenticationService;
import com.sergosoft.polymate.auth.RegisterRequest;
import com.sergosoft.polymate.service.impl.UserEntityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserEntityServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        if (userService.userExistsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthenticationResponse("Email already exists"));
        }

        if (userService.userExistsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthenticationResponse("Username already exists"));
        }

        try {
            return ResponseEntity.ok(authenticationService.register(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("Unable to register new user."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("Unable to login."));
        }
    }

    @GetMapping("/exists")
    public boolean userExists(@RequestParam String email) {
        return authenticationService.existsByEmail(email);
    }
}
