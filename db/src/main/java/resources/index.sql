USE myDatabase

CREATE TABLE users (
    email VARCHAR(255) PRIMARY KEY,
    senha CHAR(60) NOT NULL
);

CREATE INDEX indexEmail ON users USING HASH (email);