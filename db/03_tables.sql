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
)