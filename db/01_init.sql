DROP USER IF EXISTS postgres;
CREATE USER postgres WITH SUPERUSER PASSWORD '12345678';

DROP DATABASE IF EXISTS librarydb;
CREATE DATABASE librarydb WITH OWNER postgres;