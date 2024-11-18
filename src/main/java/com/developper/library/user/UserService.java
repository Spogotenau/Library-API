package com.developper.library.user;

import com.developper.library.book.Book;
import com.developper.library.book.BookRepository;
import com.developper.library.responses.MessageResponse;
import com.developper.library.user.responses.UserGet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public UserService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<UserGet> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserGet(
                        user.getUsername(),
                        user.getBooksRead().stream()
                                .map(book -> new UserGet.BookGetTitleAndId(book.getTitle(), book.getId()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public UserGet getUserByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserGet(
                user.getUsername(),
                user.getBooksRead().stream()
                        .map(book -> new UserGet.BookGetTitleAndId(book.getTitle(), book.getId()))
                        .collect(Collectors.toList())
        );
    }

    public MessageResponse addBookToUser(String username, UUID bookId) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        user.getBooksRead().add(book);

        userRepository.save(user);

        return new MessageResponse("Successfully added book to user");
    }

    public MessageResponse removeBookFromUser(String username, UUID bookId) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean removed = user.getBooksRead().removeIf(book -> book.getId().equals(bookId));

        if (!removed) {
            throw new RuntimeException("Book not found in user's read list");
        }

        userRepository.save(user);

        return new MessageResponse("Successfully removed book from user");
    }
}
