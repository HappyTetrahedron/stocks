SET foreign_key_checks = 0;

DROP TABLE IF EXISTS `Food`;

CREATE TABLE `Food` (
  `ID` int UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
    `ID` int UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    `name` varchar(200) NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `Location`;

CREATE TABLE `Location` (
    `ID` int UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    `name` varchar(200) NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS User_device;

CREATE TABLE User_device (
    `ID` int UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    `name` varchar(200) NOT NULL,
    `last_poll` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00',
    belongs_to int UNSIGNED NOT NULL,
    CONSTRAINT `device_points_to_user` FOREIGN KEY (`belongs_to`) REFERENCES `User`(`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS Food_item;

CREATE TABLE Food_item (
    `ID` int UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    `eat_by` DATETIME NOT NULL,
    `of_type` int UNSIGNED NOT NULL,
    `stored_in` int UNSIGNED NOT NULL,
    `registers` int UNSIGNED NOT NULL,
    `buys` int UNSIGNED NOT NULL,    
    FOREIGN KEY (of_type) REFERENCES Food(`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (stored_in) REFERENCES Location(`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (registers) REFERENCES User_device(`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (buys) REFERENCES `User`(`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS Ticket;

CREATE TABLE Ticket (
    `ID` int UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    `ticket` varchar(64) NOT NULL,
    `created_on` DATETIME NOT NULL DEFAULT '2100-01-01 00:00:00',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO Ticket (ticket)
VALUES 
    ('0000000000000000000000000000000000000000000000000000000000000000');

SET foreign_key_checks = 1;
