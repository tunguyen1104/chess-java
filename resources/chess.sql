CREATE DATABASE CHESS;
USE CHESS;
CREATE TABLE ACCOUNT (
        username VARCHAR(255) NOT NULL PRIMARY KEY,
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
        FOREIGN KEY (username) REFERENCES ACCOUNT(username)
);
CREATE TABLE HISTORY (
        id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(255) NOT NULL,
        history varchar(10000) NOT NULL,
        FOREIGN KEY (username) REFERENCES ACCOUNT(username)
);
INSERT INTO ACCOUNT (username, password, email, piece, board_name, sound, puzzle_failed, puzzle_solved)
VALUES ('moriaty123', 'moriaty123', 'moriaty@gmail.com', 'resources/pieces/default.png', 'resources/board/green.png', true, '1,3,4', '2');

INSERT INTO ACCOUNT (username, password, email, piece, board_name, sound, puzzle_failed, puzzle_solved)
VALUES ('watson1887', 'doctor', 'watson@example.com', 'resources/pieces/default.png', 'resources/board/orange.png', false, '3,6,7', '1');

INSERT INTO ACCOUNT (username, password, email, piece, board_name, sound, puzzle_failed, puzzle_solved)
VALUES ('travelbug', 'adventure', 'michael.brown@gmail.com', 'resources/pieces/alpha.png', 'resources/board/metal.png', true, '5', '1,2,3,7,8,9');

INSERT INTO ACCOUNT (username, password, email, piece, board_name, sound, puzzle_failed, puzzle_solved)
VALUES ('irene_adler', 'sherlocked', 'adler@example.com', 'resources/pieces/default.png', 'resources/board/metal.png', false, '1,2,5', '3,9,10');
INSERT INTO CURRENTUSER (username) VALUES ('moriaty123');

INSERT INTO HISTORY (username, history)
VALUES ((SELECT username FROM CURRENTUSER),
        'Mon Apr 29 13:51:22 2024
        PvP
        1
        Timeout
        White win
        4644___3133___
        4433P__4142___
        3342P__6163___
        4251P_+4051P__
        6664___2064P__
        3764B__5141___
        5724___3036P_+
        2736Q__1022___
        3663P_+4132___
        6434__+2234Q__
        5654___3222___
        5453___2231___
        5352___6052P__
        7674___5244___
        6755___3426P_+
        4737___');
