package com.developper.library.auth;

import com.developper.library.auth.requests.SigninRequest;
import com.developper.library.auth.requests.SignupRequest;
import com.developper.library.errorhandling.InternalServerErrorException;
import com.developper.library.responses.MessageResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Key jwtSecret;
    private static final long JWT_EXPIRATION = 86400000;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public MessageResponse signup(SignupRequest signupRequest) {
        if (userRepository.findByUsername(signupRequest.getUsername()) != null) {
            throw new InternalServerErrorException("Username is already in use");
        }

        String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User newUser = new User(signupRequest.getUsername(), hashedPassword);
        userRepository.save(newUser);

        return new MessageResponse("Successfully created user");
    }

    public String signin(SigninRequest signinRequest) {
        User user = userRepository.findByUsername(signinRequest.getUsername());
        if (user == null) {
            throw new InternalServerErrorException("User not found");
        }

        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new InternalServerErrorException("Invalid password");
        }

        return generateJwtToken(user);
    }

    private String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION))
                .signWith(jwtSecret)
                .compact();
    }
}
