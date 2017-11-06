-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


DROP SCHEMA IF EXISTS `sns` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema sns
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sns
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sns` DEFAULT CHARACTER SET utf8 ;
USE `sns` ;

-- -----------------------------------------------------
-- Table `sns`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sns`.`user` (
  `user_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_name`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sns`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sns`.`image` (
  `image_id` INT(11) NOT NULL AUTO_INCREMENT,
  `image_name` VARCHAR(45) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `image_url` VARCHAR(300) NOT NULL,
  `comments` VARCHAR(200) NULL DEFAULT NULL,
  `user_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  INDEX `username_idx` (`user_name` ASC),
  CONSTRAINT `username`
    FOREIGN KEY (`user_name`)
    REFERENCES `sns`.`user` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sns`.`user_friend`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sns`.`user_friend` (
  `friend_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user` VARCHAR(45) NULL DEFAULT NULL,
  `friend` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`friend_id`),
  INDEX `username_idx` (`user` ASC),
  INDEX `friend_idx` (`friend` ASC),
  CONSTRAINT `friend`
    FOREIGN KEY (`friend`)
    REFERENCES `sns`.`user` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user`
    FOREIGN KEY (`user`)
    REFERENCES `sns`.`user` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `index1` ON `sns`.`user_friend` (`user`,`friend`); 

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
