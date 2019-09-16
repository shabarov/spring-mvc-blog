create DATABASE `blog_spring_mvc` CHARACTER SET utf8 COLLATE utf8_general_ci;

use `blog_spring_mvc`;

CREATE TABLE categories (
	categoryId INT(11) NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (categoryId)
) ;

CREATE TABLE posts (
	postId INT(11) NOT NULL AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL, 
	summary TEXT NOT NULL, 
	body TEXT NOT NULL,
	postDate VARCHAR(20) NOT NULL,
	imagePath VARCHAR(255) NOT NULL,
	categoryId_fk INT(11) NOT NULL ,
	PRIMARY KEY (postId)
) ;

CREATE TABLE comments (
	commentId INT(11) NOT NULL AUTO_INCREMENT,
	body TEXT NOT NULL ,
	commentDate VARCHAR(20) NOT NULL ,
	author VARCHAR(255) NOT NULL ,
	email VARCHAR(255) NOT NULL ,
	postId_fk INT(11) NOT NULL ,
	PRIMARY KEY (commentId)
) ;
	
ALTER TABLE posts ADD CONSTRAINT CONSTR_POST_CATEGORY FOREIGN KEY (categoryId_fk) REFERENCES categories (categoryId);
ALTER TABLE comments ADD CONSTRAINT CONSTR_COMMENT_POST FOREIGN KEY (postId_fk) REFERENCES posts (postId);

CREATE TABLE `users` (
	`USER_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`USERNAME` VARCHAR(45) NOT NULL,
	`PASSWORD` VARCHAR(45) NOT NULL,
	`EMAIL` VARCHAR(45) NOT NULL,
	`ENABLED` tinyint(1) NOT NULL,
	PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_roles` (
	`USER_ROLE_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`USER_ID` INT(10) UNSIGNED NOT NULL,
	`AUTHORITY` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`USER_ROLE_ID`),
	KEY `FK_user_roles` (`USER_ID`),
	CONSTRAINT `FK_user_roles` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `categories` (`categoryId`, `name`) VALUES
  (1,'News'),
  (2,'Java');

INSERT INTO `posts` (`postId`, `title`, `summary`, `body`, `postDate`, `imagePath`, `categoryId_fk`) VALUES
  (1,'Test title', 'Test summary', 'Test body', '2016-01-01 14:56:34', 'imagePath1', 1);

INSERT INTO `posts` (`postId`, `title`, `summary`, `body`, `postDate`, `imagePath`, `categoryId_fk`) VALUES
  (2,'Title', 'Test summary', 'Test body', '2016-01-01 14:22:34', 'imagePath2', 1);

INSERT INTO `posts` (`postId`, `title`, `summary`, `body`, `postDate`, `imagePath`, `categoryId_fk`) VALUES
	(3,'InsertedByScript', 'scr', 'scr', '2016-01-21 14:56:34', 'imagePath1', 1);

INSERT INTO `comments` (`commentId`, `body`, `commentDate`, `author`, `email`, `postId_fk`) VALUES
	(1, 'This is amazing post', '2017-06-11 15:33:09', 'John', 'a@m.ru', 1);

INSERT INTO `comments` (`commentId`, `body`, `commentDate`, `author`, `email`, `postId_fk`) VALUES
	(2, 'This is bullshit, man', '2017-06-11 15:33:03', 'Mikhail', 'a@m.ru', 1);

INSERT INTO users (USERNAME,PASSWORD,EMAIL,ENABLED) VALUES ('admin', 'adm', 'admin@mail.ru', TRUE);
INSERT INTO users (USERNAME,PASSWORD,EMAIL,ENABLED) VALUES ('user', 'user', 'user@mail.ru', TRUE);

INSERT INTO user_roles (USER_ID,AUTHORITY) VALUES (1, 'ROLE_USER');
INSERT INTO user_roles (USER_ID,AUTHORITY) VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_roles (USER_ID,AUTHORITY) VALUES (2, 'ROLE_USER');

COMMIT;