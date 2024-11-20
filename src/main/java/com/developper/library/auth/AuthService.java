package com.developper.library.auth;

import com.developper.library.auth.requests.SigninRequest;
import com.developper.library.auth.requests.SignupRequest;
import com.developper.library.errorhandling.ConflictException;
import com.developper.library.errorhandling.InternalServerErrorException;
import com.developper.library.errorhandling.InvalidLoginException;
import com.developper.library.responses.MessageResponse;
import com.developper.library.user.User;
import com.developper.library.user.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.developper.library.auth.Role.ROLE_USER;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String signin(SigninRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtService.generateJwtToken(authentication);
        } catch (BadCredentialsException e) {
            throw new InvalidLoginException("Invalid username or password.");
        }
    }

    public MessageResponse signup(SignupRequest request) {
        try {
            if (userRepository.findById(request.getUsername()).isPresent()) {
                throw new ConflictException("Username is already taken");
            }

            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(ROLE_USER);

            userRepository.save(user);
            return new MessageResponse("User registered successfully!");
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Failed to register user.");
        }

    }
}
