package com.developper.library.book;
import com.developper.library.book.requests.BookCreate;
import com.developper.library.errorhandling.InternalServerErrorException;
import com.developper.library.errorhandling.NotFoundException;
import com.developper.library.responses.IdResponse;
import com.developper.library.responses.MessageResponse;
import org.springframework.dao.DataAccessException;
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
        try {
            return bookRepository.findAll();
        } catch (DataAccessException e){
            throw new InternalServerErrorException("Failed to get all books");
        }
    }

    public Book getBookById(UUID id) {
        try {
            return bookRepository.findBookById(id)
                    .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
        } catch (DataAccessException e){
            throw new InternalServerErrorException("Failed to get book with id: " + id);
        }

    }

    public Book create(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataAccessException e){
            throw new InternalServerErrorException("Failed to create book");
        }

    }

    public IdResponse update(Book bookUpdate) {
        try {
            if (!bookRepository.existsById(bookUpdate.getId())) {
                throw new NotFoundException("Book with id " + bookUpdate.getId() + " not found");
            }
            Book updatedBook = bookRepository.save(bookUpdate);

            return new IdResponse(updatedBook.getId(), "Book was successfully updated");
        } catch (DataAccessException e){
            throw new InternalServerErrorException("Failed to update book");
        }

    }

    public MessageResponse delete(UUID id) {
        try {
            if(!bookRepository.existsById(id)) {
                throw new NotFoundException("Book with id " + id + " not found");
            }
            bookRepository.deleteById(id);

            return new MessageResponse("Successfully deleted");
        } catch (DataAccessException e){
            throw new InternalServerErrorException("Failed to delete book");
        }

    }
}
