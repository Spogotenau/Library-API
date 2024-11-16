package com.developper.library.auth;

import com.developper.library.auth.requests.SigninRequest;
import com.developper.library.auth.requests.SignupRequest;
import com.developper.library.responses.MessageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="User")
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@RequestBody SignupRequest request){
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody SigninRequest request){
        return ResponseEntity.ok(authService.signin(request));
    }
}
