package com.developper.library.user;

import com.developper.library.book.Book;
import com.developper.library.book.BookRepository;
import com.developper.library.errorhandling.ConflictException;
import com.developper.library.errorhandling.InternalServerErrorException;
import com.developper.library.errorhandling.NotFoundException;
import com.developper.library.responses.MessageResponse;
import com.developper.library.user.responses.UserGet;
import org.springframework.dao.DataAccessException;
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
        try {
            return userRepository.findAll().stream()
                    .map(user -> new UserGet(
                            user.getUsername(),
                            user.getBooksRead().stream()
                                    .map(book -> new UserGet.BookGetTitleAndId(book.getTitle(), book.getId()))
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Failed to get users");
        }
    }

    public UserGet getUserByUsername(String username) {
        try {
            User user = userRepository.findById(username)
                    .orElseThrow(() -> new NotFoundException("User named " + username));

            return new UserGet(
                    user.getUsername(),
                    user.getBooksRead().stream()
                            .map(book -> new UserGet.BookGetTitleAndId(book.getTitle(), book.getId()))
                            .collect(Collectors.toList())
            );
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Failed to get user " + username);
        }

    }

    public MessageResponse addBookToUser(String username, UUID bookId) {
        try {
            User user = userRepository.findById(username)
                    .orElseThrow(() -> new NotFoundException("User named " + username));
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new NotFoundException("Book with ID: " + bookId));

            if (user.getBooksRead().contains(book)) {
                throw new ConflictException("Book with ID: " + bookId + " is already added to user: " + username);
            }

            user.getBooksRead().add(book);

            userRepository.save(user);

            return new MessageResponse("Successfully added book to user");
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Failed to add book to user");
        }

    }

    public MessageResponse removeBookFromUser(String username, UUID bookId) {
        try {
            User user = userRepository.findById(username)
                    .orElseThrow(() -> new NotFoundException("User named " + username));

            boolean removed = user.getBooksRead().removeIf(book -> book.getId().equals(bookId));

            if (!removed) {
                throw new NotFoundException("Book with ID: " + bookId + " not found in user's read list");
            }

            userRepository.save(user);

            return new MessageResponse("Successfully removed book from user");
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Failed to remove book from user");
        }

    }
}
