package com.railway.userservice.controller;

import com.railway.userservice.dto.AuthRequest;
import com.railway.userservice.dto.AuthResponse;
import com.railway.userservice.entity.User;
import com.railway.userservice.service.UserService;
import com.railway.userservice.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        userService.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/details")
    public ResponseEntity<User> getUserDetailsByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            // Authenticate user
            Authentication auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            Authentication authenticated = authManager.authenticate(auth);

            // Fetch full user from DB
            User user = userService.findByEmail(request.getEmail()).get();
            
            // Generate JWT
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());

            // Set authentication in context (optional if only for token-based systems)
            System.out.println("Setting authentication for user: " + user);
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            System.out.println("✅ Authenticated: " + user.getEmail());

            return ResponseEntity.ok(new AuthResponse(token));
            
        } catch (Exception ex) {
        	System.out.println("❌ Login failed: " + ex.getMessage());
            return ResponseEntity.status(403).body(new AuthResponse("Invalid credentials"));
        }
    }

    
}


