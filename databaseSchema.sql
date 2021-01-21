-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema mjc_module_2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mjc_module_2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mjc_module_2` DEFAULT CHARACTER SET utf8 ;
USE `mjc_module_2` ;

-- -----------------------------------------------------
-- Table `mjc_module_2`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc_module_2`.`gift_certificate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(90) NOT NULL,
  `description` VARCHAR(90) NOT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  `duration` INT NOT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mjc_module_2`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc_module_2`.`tag` (
  `id_tag` INT NOT NULL AUTO_INCREMENT,
  `name_tag` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`id_tag`),
  UNIQUE INDEX `name_tag_UNIQUE` (`name_tag` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 51
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mjc_module_2`.`gift_certificate_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mjc_module_2`.`gift_certificate_has_tag` (
  `gift_certificate_id_gift_certificate` INT NOT NULL,
  `tag_id_tag` INT NOT NULL,
  PRIMARY KEY (`gift_certificate_id_gift_certificate`, `tag_id_tag`),
  INDEX `fk_gift_certificate_has_tag_tag1_idx` (`tag_id_tag` ASC) VISIBLE,
  INDEX `fk_gift_certificate_has_tag_gift_certificate_idx` (`gift_certificate_id_gift_certificate` ASC) VISIBLE,
  CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
    FOREIGN KEY (`gift_certificate_id_gift_certificate`)
    REFERENCES `mjc_module_2`.`gift_certificate` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_gift_certificate_has_tag_tag`
    FOREIGN KEY (`tag_id_tag`)
    REFERENCES `mjc_module_2`.`tag` (`id_tag`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
