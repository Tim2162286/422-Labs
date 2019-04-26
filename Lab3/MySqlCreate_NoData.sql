CREATE DATABASE `phonebook`;

CREATE TABLE phonebook.books (
  `BOOK` varchar(45) NOT NULL,
  PRIMARY KEY (`BOOK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE phonebook.listings (
  `PHONE` varchar(15) NOT NULL,
  `FIRST` varchar(45) NOT NULL,
  `LAST` varchar(45) NOT NULL,
  `PHONEBOOK` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PHONE`),
  FOREIGN KEY (`PHONEBOOK`) REFERENCES BOOKS(BOOK)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

