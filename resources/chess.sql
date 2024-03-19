CREATE DATABASE CHESS;
USE CHESS;
CREATE TABLE ACCOUNT (
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    piece VARCHAR(200) NOT NULL,
    board_name VARCHAR(200) NOT NULL,
    sound BOOLEAN NOT NULL,
    puzzle_failed VARCHAR(500) NOT NULL,
    puzzle_solved VARCHAR(500) NOT NULL
);
CREATE TABLE CURRENTUSER (
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    piece VARCHAR(200) NOT NULL,
    board_name VARCHAR(200) NOT NULL,
    sound BOOLEAN NOT NULL,
    puzzle_failed VARCHAR(500) NOT NULL,
    puzzle_solved VARCHAR(500) NOT NULL
);

INSERT INTO CURRENTUSER (username, password, email, piece, board_name, sound, puzzle_failed, puzzle_solved)
VALUES ('moriaty123', 'moriaty123', 'moriaty@gmail.com', 'src/res/pieces/maestro.png', 'src/res/board/metal.png', true, '1,3,4', '2,5');

INSERT INTO ACCOUNT (username, password, email, piece, board_name, sound, puzzle_failed, puzzle_solved)
VALUES ('moriaty123', 'moriaty123', 'moriaty@gmail.com', 'src/res/pieces/default.png', 'src/res/board/green.png', true, '1,3,4', '2');

INSERT INTO ACCOUNT (username, password, email, piece, board_name, sound, puzzle_failed, puzzle_solved)
VALUES ('watson1887', 'doctor', 'watson@example.com', 'src/res/pieces/default.png', 'src/res/board/red.png', false, '3,6,7', '1');

INSERT INTO ACCOUNT (username, password, email, piece, board_name, sound, puzzle_failed, puzzle_solved)
VALUES ('travelbug', 'adventure', 'michael.brown@gmail.com', 'src/res/pieces/alpha.png', 'src/res/board/metal.png', true, '5', '1,2,3,7,8,9');

INSERT INTO ACCOUNT (username, password, email, piece, board_name, sound, puzzle_failed, puzzle_solved)
VALUES ('irene_adler', 'sherlocked', 'adler@example.com', 'src/res/pieces/default.png', 'src/res/board/purple.png', false, '1,2,5', '3,9,10');
