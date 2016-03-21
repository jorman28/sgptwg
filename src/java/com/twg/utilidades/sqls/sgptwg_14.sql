ALTER TABLE `estados` 
CHANGE `estado_prev` `estado_previo` INT(11) NULL DEFAULT NULL, 
CHANGE `estado_sig` `estado_siguiente` INT(11) NULL DEFAULT NULL, 
CHANGE `e_final` `estado_final` VARCHAR(1) NOT NULL DEFAULT 'F';