drop database if exists lubricentro;
create database lubricentro;

use lubricentro;

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
ALTER TABLE `lubricentro`.`articulos` ADD COLUMN `stock_minimo` INT(11) NULL DEFAULT 0  AFTER `proveedor_id` ;

CREATE  TABLE `lubricentro`.`clientes` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NULL ,
  `telefono` VARCHAR(45) NULL ,
  `celular` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );

CREATE  TABLE `lubricentro`.`proveedors` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(100) NOT NULL ,
  `telefono` VARCHAR(100) NULL ,
  PRIMARY KEY (`id`) );

