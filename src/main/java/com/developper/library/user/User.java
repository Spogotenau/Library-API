package com.developper.library.user;

import com.developper.library.auth.Role;
import com.developper.library.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(unique = true, nullable = false, length = 32)
    private String username;

    @Column(nullable = false, length = 128)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "user_books",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> booksRead;
}
