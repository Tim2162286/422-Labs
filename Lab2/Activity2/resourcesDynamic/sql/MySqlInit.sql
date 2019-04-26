CREATE TABLE `listings` (
  `PHONE` varchar(15) NOT NULL,
  `FIRST` varchar(45) DEFAULT NULL,
  `LAST` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PHONE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `listings` (`PHONE`,`FIRST`,`LAST`) VALUES
('4807300465', 'Brad', 'Thompson'),
('8372953', 'Ange', 'Scanlan'),
('7349326', 'Chris', 'Bierwagon'),
('4806948609', 'Tim', 'Cuprak'),
('734024234', 'John', 'Deer');
