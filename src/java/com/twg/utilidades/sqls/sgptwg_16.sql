ALTER TABLE actividades_empleados ADD COLUMN fecha_estimada_inicio DATE NOT NULL AFTER empleado;
ALTER TABLE actividades_empleados ADD COLUMN fecha_estimada_terminacion DATE NOT NULL AFTER fecha_estimada_inicio;
ALTER TABLE actividades_empleados ADD COLUMN tiempo_estimado DECIMAL(10,2) NOT NULL AFTER fecha_estimada_terminacion;
ALTER TABLE actividades_empleados ADD COLUMN fecha_real_inicio DATE AFTER NULL tiempo_estimado;
ALTER TABLE actividades_empleados ADD COLUMN fecha_real_terminacion DATE NULL AFTER fecha_real_inicio;
ALTER TABLE actividades_empleados ADD COLUMN tiempo_invertido DECIMAL(10, 2) NULL AFTER tiempo_estimado;

ALTER TABLE actividades DROP COLUMN fecha_estimada_inicio;
ALTER TABLE actividades DROP COLUMN fecha_estimada_terminacion;
ALTER TABLE actividades DROP COLUMN tiempo_estimado;
ALTER TABLE actividades DROP COLUMN tiempo_invertido;
ALTER TABLE actividades DROP COLUMN fecha_real_inicio;
ALTER TABLE actividades DROP COLUMN fecha_real_terminacion;
