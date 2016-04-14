ALTER TABLE `estados` 
ADD `estado_previo` INT NULL AFTER `tipo_estado`, 
ADD `estado_siguiente` INT NULL AFTER `estado_previo`, 
ADD `estado_final` VARCHAR(1) NOT NULL DEFAULT 'F' AFTER `estado_siguiente`;