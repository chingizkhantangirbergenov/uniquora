CREATE SCHEMA `uniquora`;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    firstName varchar(255),
    lastName varchar(255),
    email varchar(255),
    hashedPassword varchar(255),
    userCode varchar(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;