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
        UUID id = UUID.randomUUID();
        Book book = new Book(id, bookCreate.getTitle(), bookCreate.getAuthor(), bookCreate.getPages());

        return ResponseEntity.ok(bookService.create(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdResponse> update(@RequestBody Book bookUpdate, @PathVariable UUID id) {
        Book book = new Book(id, bookUpdate.getTitle(), bookUpdate.getAuthor(), bookUpdate.getPages());

        return ResponseEntity.ok(bookService.update(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(@PathVariable UUID id){
        return ResponseEntity.ok(bookService.delete(id));
    }
}
