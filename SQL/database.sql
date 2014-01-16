create database lubricentro;

CREATE  TABLE `lubricentro`.`articulos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `codigo` VARCHAR(45) NOT NULL ,
  `descripcion` VARCHAR(150) NULL ,
  `marca` VARCHAR(100) NULL ,
  `stock` INT NULL DEFAULT 0 ,
  `precio_compra` FLOAT NULL DEFAULT 0 ,
  `precio_venta` FLOAT NULL DEFAULT 0 ,
  `equivalencia_fram` VARCHAR(100) NULL ,
  PRIMARY KEY (`id`) );

ALTER TABLE `lubricentro`.`articulos` ADD COLUMN `proveedor_id` INT(9) NULL  AFTER `equivalencia_fram` ;