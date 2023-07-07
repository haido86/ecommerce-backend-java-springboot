package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

    public Map<String, String> loginUser(AuthRequest authRequest){
        Map<String, String> token = new HashMap<>();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        User user = userRepository.findByUsername(authRequest.getUsername());
        token.put("token", jwtUtils.generateToken(user));
        return token;
    }

    public User createOne(User user) {
        User newUser = new User(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                User.Role.USER
        );
        return userRepository.save(newUser);
    }

    public void deleteOne(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}

