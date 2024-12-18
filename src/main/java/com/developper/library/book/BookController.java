package com.developper.library.book;

import com.developper.library.book.requests.BookCreate;
import com.developper.library.responses.IdResponse;
import com.developper.library.responses.MessageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "Book")
@RequestMapping("/book")
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> all() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<IdResponse> create(@RequestBody BookCreate bookCreate) {
        Book book = new Book(null, bookCreate.getTitle(), bookCreate.getAuthor(), bookCreate.getPages(), null);

        Book createdBook = bookService.create(book);

        return ResponseEntity.ok(new IdResponse(createdBook.getId(), "Book was successfully created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdResponse> update(@RequestBody Book bookUpdate, @PathVariable UUID id) {
        Book existingBook = bookService.getBookById(id);

        existingBook.setTitle(bookUpdate.getTitle());
        existingBook.setAuthor(bookUpdate.getAuthor());
        existingBook.setPages(bookUpdate.getPages());

        IdResponse response = bookService.update(existingBook);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(@PathVariable UUID id){
        return ResponseEntity.ok(bookService.delete(id));
    }
}
