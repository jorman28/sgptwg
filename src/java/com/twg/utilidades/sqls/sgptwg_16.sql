ALTER TABLE actividades_empleados ADD COLUMN fecha_estimada_inicio DATE AFTER empleado;
ALTER TABLE actividades_empleados ADD COLUMN fecha_estimada_terminacion DATE AFTER fecha_estimada_inicio;
ALTER TABLE actividades_empleados ADD COLUMN tiempo_estimado DECIMAL(10,2) AFTER fecha_estimada_terminacion;

ALTER TABLE actividades DROP COLUMN fecha_estimada_inicio;
ALTER TABLE actividades DROP COLUMN fecha_estimada_terminacion;
ALTER TABLE actividades DROP COLUMN tiempo_estimado;
ALTER TABLE actividades DROP COLUMN tiempo_invertido;
ALTER TABLE actividades DROP COLUMN fecha_real_inicio;
ALTER TABLE actividades DROP COLUMN fecha_real_terminacion;

ALTER TABLE actividades ADD COLUMN nombre VARCHAR(80) DEFAULT '' AFTER version;
