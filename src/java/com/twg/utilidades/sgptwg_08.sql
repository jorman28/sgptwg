ALTER TABLE `actividades` CHANGE `tiempo_estimado` `tiempo_estimado` DECIMAL(11,1) NOT NULL, CHANGE `tiempo_invertido` `tiempo_invertido` DECIMAL(11,1) NULL DEFAULT NULL;
ALTER TABLE actividades DROP FOREIGN KEY actividades_estados_fk;
ALTER TABLE actividades ADD FOREIGN KEY (estado) REFERENCES estados(id);  
