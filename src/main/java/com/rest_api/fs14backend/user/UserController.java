package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/auth/signin")
    public Map<String, String> login(@RequestBody AuthRequest authRequest) {
        return userService.loginUser(authRequest);
    }

    @PostMapping("/auth/signup")
    public User signup(@RequestBody User user) {
        return userService.createOne(user);
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteOne(id);
    }
}
