ALTER TABLE actividades_esfuerzos MODIFY COLUMN tiempo DOUBLE NOT NULL;
ALTER TABLE actividades_empleados ADD COLUMN fecha_eliminacion DATETIME NULL AFTER empleado;