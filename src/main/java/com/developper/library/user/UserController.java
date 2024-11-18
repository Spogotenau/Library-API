package com.developper.library.user;

import com.developper.library.responses.MessageResponse;
import com.developper.library.user.responses.UserGet;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name="User")
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserGet>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserGet> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/{username}/add-book/{bookId}")
    public ResponseEntity<MessageResponse> addBookToUser(@PathVariable String username, @PathVariable UUID bookId) {
        return ResponseEntity.ok(userService.addBookToUser(username, bookId));
    }

    @DeleteMapping("/{username}/remove-book/{bookId}")
    public ResponseEntity<MessageResponse> removeBookFromUser(@PathVariable String username, @PathVariable UUID bookId) {
        return ResponseEntity.ok(userService.removeBookFromUser(username, bookId));
    }
}
