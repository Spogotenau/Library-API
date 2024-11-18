package com.developper.library.book;

import com.developper.library.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 32)
    private String title;

    @Column(nullable = false, length = 32)
    private String author;

    @Column(nullable = false)
    private int pages;

    @ManyToMany(mappedBy = "booksRead")
    @JsonIgnore
    private List<User> readers;
}
