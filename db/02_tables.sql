DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE book
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title   VARCHAR(32) NOT NULL,
    author  VARCHAR(32) NOT NULL,
    pages   INTEGER NOT NULL
);