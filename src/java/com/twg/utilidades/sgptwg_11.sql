ALTER TABLE `estados` 
ADD `estadoPrev` INT NULL AFTER `tipo_estado`, 
ADD `estadoSig` INT NULL AFTER `estadoPrev`, 
ADD `eFinal` VARCHAR(1) NOT NULL DEFAULT 'F' AFTER `estadoSig`;