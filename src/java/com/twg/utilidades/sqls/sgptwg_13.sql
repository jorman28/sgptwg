
ALTER TABLE `estados` 
CHANGE `estadoPrev` `estado_prev` INT(11) NULL DEFAULT NULL, 
CHANGE `estadoSig` `estado_sig` INT(11) NULL DEFAULT NULL, 
CHANGE `eFinal` `e_final` VARCHAR(1) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL DEFAULT 'F';