-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema tsum-test
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tsum-test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tsum-test` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `tsum-test` ;

-- -----------------------------------------------------
-- Table `tsum-test`.`Brand`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tsum-test`.`Brand` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tsum-test`.`BrandTag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tsum-test`.`BrandTag` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `externalId` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `externalId_UNIQUE` (`externalId` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tsum-test`.`Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tsum-test`.`Client` (
  `Client` INT(11) NOT NULL,
  PRIMARY KEY (`Client`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tsum-test`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tsum-test`.`Product` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(256) CHARACTER SET 'ucs2' NOT NULL,
  `sizes` VARCHAR(256) NULL DEFAULT NULL,
  `price` INT(11) NULL DEFAULT NULL,
  `newPrice` INT(11) NULL DEFAULT NULL,
  `actionEnabled` TINYINT(1) NOT NULL DEFAULT '0',
  `actionDetails` VARCHAR(1024) NULL DEFAULT NULL,
  `brandTagId` INT(11) NOT NULL,
  `brandId` INT(11) NULL DEFAULT NULL,
  `smallPhotoURL` VARCHAR(1024) NOT NULL,
  `largePhotoURL` VARCHAR(1024) NOT NULL,
  `updateTimestamp` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_brandTagId1_idx` (`brandTagId` ASC),
  INDEX `fk_product_brand1_idx` (`brandId` ASC),
  CONSTRAINT `fk_product_brand1`
    FOREIGN KEY (`brandId`)
    REFERENCES `tsum-test`.`Brand` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_brandtag`
    FOREIGN KEY (`brandTagId`)
    REFERENCES `tsum-test`.`BrandTag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tsum-test`.`UserFavoriteProduct`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tsum-test`.`ClientProduct` (
  `product_id` INT(11) NOT NULL,
  `client_id` INT(11) NOT NULL,
  PRIMARY KEY (`product_id`, `client_id`),
  INDEX `fk_product_has_user_user1_idx` (`client_id` ASC),
  INDEX `fk_product_has_user_product1_idx` (`product_id` ASC),
  CONSTRAINT `fk_product_has_user_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `tsum-test`.`Product` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_user_user1`
    FOREIGN KEY (`client_id`)
    REFERENCES `tsum-test`.`Client` (`Client`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

