CREATE DATABASE poll_db;
USE poll_db;

CREATE TABLE poll (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  PRIMARY KEY (id));

CREATE TABLE poll_choice (
  id INT NOT NULL AUTO_INCREMENT,
  choice VARCHAR(255) NULL,
  poll_id INT NULL,
  PRIMARY KEY (id),
  INDEX Poll_id (poll_id ASC),
  CONSTRAINT poll
  FOREIGN KEY (poll_id)
  REFERENCES poll (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE poll_answer (
  id INT NOT NULL  AUTO_INCREMENT ,
  poll_choice_id INT NULL,
  user VARCHAR(45) NULL,
  PRIMARY KEY (id),
  INDEX poll_choice_id (poll_choice_id ASC),
  CONSTRAINT poll_choice_id
  FOREIGN KEY (poll_choice_id)
  REFERENCES poll_choice (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
