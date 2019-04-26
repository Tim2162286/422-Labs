CREATE DATABASE `tjcuprak_phonebook`;

CREATE TABLE tjcuprak_phonebook.books (
  `BOOK` varchar(45) NOT NULL,
  PRIMARY KEY (`BOOK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `books`(`book`) VALUES
('Book1'),
('Book2'),
('Book3'),
('Book4');

CREATE TABLE tjcuprak_phonebook.listings (
  `PHONE` varchar(15) NOT NULL,
  `FIRST` varchar(45) NOT NULL,
  `LAST` varchar(45) NOT NULL,
  `PHONEBOOK` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PHONE`),
  FOREIGN KEY (`PHONEBOOK`) REFERENCES BOOKS(BOOK)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO listings(`PHONE`, `FIRST`, `LAST`, `PHONEBOOK`) VALUES
('324234','Bob','Doe','Book1'),
('325423','Jhon','Doe','Book1'),
('2134235','Jane','Doe','Book2');
INSERT INTO listings(`PHONE`, `FIRST`, `LAST`) VALUES
('123234','Tim','Cuprak'),
('2021060','Rich','Cuprak');
