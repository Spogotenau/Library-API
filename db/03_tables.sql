DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE book
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title   VARCHAR(32) NOT NULL,
    author  VARCHAR(32) NOT NULL,
    pages   INTEGER NOT NULL
);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    username VARCHAR(32) PRIMARY KEY NOT NULL,
    password VARCHAR(128) NOT NULL,
    role role_type NOT NULL
);

DROP TABLE IF EXISTS user_books CASCADE;
CREATE TABLE user_books
(
    username VARCHAR(32) NOT NULL,
    book_id UUID NOT NULL,
    PRIMARY KEY (username, book_id),
    FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE
);