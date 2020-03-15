-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sys_votacao
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sys_votacao
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sys_votacao` DEFAULT CHARACTER SET utf8 ;
USE `sys_votacao` ;

-- -----------------------------------------------------
-- Table `sys_votacao`.`associado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_votacao`.`associado` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sys_votacao`.`pauta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_votacao`.`pauta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(200) NULL,
  `inicio` DATETIME NULL,
  `fim` DATETIME NULL,
  `total_sim` INT NULL DEFAULT 0,
  `total_nao` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sys_votacao`.`votacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_votacao`.`votacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pauta_id` INT NOT NULL,
  `associado_id` INT NOT NULL,
  `voto` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_id_Associado_idx` (`associado_id` ASC) VISIBLE,
  UNIQUE INDEX `unique_id_Pauta_id_Associado` (`pauta_id` ASC, `associado_id` ASC) VISIBLE,
  CONSTRAINT `fk_id_Pauta`
    FOREIGN KEY (`pauta_id`)
    REFERENCES `sys_votacao`.`pauta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_Associado`
    FOREIGN KEY (`associado_id`)
    REFERENCES `sys_votacao`.`associado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
