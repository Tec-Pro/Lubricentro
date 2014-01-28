drop database if exists lubricentro;
create database lubricentro;

use lubricentro;

CREATE  TABLE `lubricentro`.`articulos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `codigo` VARCHAR(45) NOT NULL ,
  `descripcion` VARCHAR(500) NULL ,
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

create table lubricentro.ventas (
    id integer not null auto_increment,
    monto float,
    cliente_id integer,
    fecha date not null,
    pago int,
    PRIMARY KEY (`id`) );

create table lubricentro.compras (
    id integer not null auto_increment,
    monto float,
    proveedor_id integer,
    fecha date not null,
	pago int,
    PRIMARY KEY (`id`) );



create table clientes_articulos(
    id integer not null auto_increment,
    cliente_id integer,
    articulos_id integer,
    cantidad integer not null,
	check (cantidad>0),
    primary key(id) );


create table articulos_ventas (
    id integer not null auto_increment,
    venta_id integer,
    articulos_id integer,
    cantidad float not null,
	check (cantidad>0),
    primary key(id) );


create table articulos_compras (
    id integer not null auto_increment,
    compra_id integer,
    articulos_id integer,
    cantidad float not null,
	check (cantidad>0),
    primary key(id));

create table usuarios (
	id integer not null auto_increment,
	nombre varchar(50) default 'sapolubricentro',
	pass varchar(50) default 'sapolubricentro',
	primary key(id));


CREATE  TABLE `lubricentro`.`pagos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `fecha` VARCHAR(45) NULL ,
  `monto` FLOAT NULL ,
  `proveedor_id` INT NULL ,
  PRIMARY KEY (`id`) );

ALTER TABLE `lubricentro`.`pagos` CHANGE COLUMN `fecha` `fecha` DATE NULL DEFAULT NULL  ;


CREATE  TABLE `lubricentro`.`emails` (
  `email` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NULL ,
  PRIMARY KEY (`email`) );

CREATE  TABLE `lubricentro`.`envios` (
  `fecha` DATE NOT NULL ,
  `enviado` VARCHAR(5) NULL ,
  PRIMARY KEY (`fecha`) );
ALTER TABLE `lubricentro`.`email` ADD COLUMN `id` VARCHAR(45) NOT NULL  AFTER `password` 
, DROP PRIMARY KEY 
, ADD PRIMARY KEY (`email`, `id`) ;


ALTER TABLE `lubricentro`.`emails` CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT  
, DROP PRIMARY KEY 
, ADD PRIMARY KEY (`id`, `email`) ;


ALTER TABLE `lubricentro`.`envio_email` ADD COLUMN `id` INT NOT NULL AUTO_INCREMENT  AFTER `enviado` 
, DROP PRIMARY KEY 
, ADD PRIMARY KEY (`id`, `fecha`) , RENAME TO  `lubricentro`.`envios` ;

ALTER TABLE `lubricentro`.`envios` CHANGE COLUMN `enviado` `enviado` INT NULL DEFAULT NULL  ;
