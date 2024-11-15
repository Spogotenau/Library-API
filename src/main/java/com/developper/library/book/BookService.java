package com.developper.library.book;
import com.developper.library.book.requests.BookCreate;
import com.developper.library.errorhandling.InternalServerErrorException;
import com.developper.library.responses.IdResponse;
import com.developper.library.responses.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(UUID id) {
        return bookRepository.findBookById(id)
                .orElseThrow(() -> new InternalServerErrorException("Failed to load book"));
    }

    public IdResponse create(Book book) {
        bookRepository.save(book);

        return new IdResponse(book.getId(), "Book was successfully created");
    }

    public IdResponse update(Book bookUpdate) {
        if (!bookRepository.existsById(bookUpdate.getId())) {
            throw new InternalServerErrorException("Failed to update book");
        }
        Book updatedBook = bookRepository.save(bookUpdate);

        return new IdResponse(updatedBook.getId(), "Book was successfully updated");
    }

    public MessageResponse delete(UUID id) {
        if(!bookRepository.existsById(id)) {
            throw new InternalServerErrorException("Failed to delete book");
        }
        bookRepository.deleteById(id);

        return new MessageResponse("Successfully deleted");
    }
}
