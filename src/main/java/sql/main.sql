CREATE SCHEMA `uniquora`;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    firstName varchar(255),
    lastName varchar(255),
    email varchar(255),
    major varchar(255),
    hashedPassword varchar(255),
    userCode varchar(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS all_users;
CREATE TABLE all_users (
    id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    email_ varchar(255),
    fullname_ varchar(255),
    id_number_ varchar(255),
    school_ varchar(255),
	  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS all_courses;
CREATE TABLE all_courses (
    id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    abbr_ varchar(255),
    name_ varchar(255),
	  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS current_courses;
CREATE TABLE current_courses (
    id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id int(10) UNSIGNED,
    course_id int(10) UNSIGNED,
	  PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES all_courses(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS completed_courses;
CREATE TABLE completed_courses (
    id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id int(10) UNSIGNED,
    course_id int(10) UNSIGNED,
    credit_ int(10),
    grade_ varchar(255),
	  PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES all_courses(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS syllabus;
CREATE TABLE syllabus (
    id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    current_course_id int(10) UNSIGNED,
    title VARCHAR(255),
    weight int(10),
    grade int(10),
    contribution int(10),
	  PRIMARY KEY (id),
    FOREIGN KEY (current_course_id) REFERENCES current_courses(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
