ALTER TABLE actividades ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE actividades_empleados ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE actividades_esfuerzos ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE archivos ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE auditorias ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE cargos ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE clientes ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE comentarios ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE comentarios_archivos ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE comentarios_referenciados ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE empleados ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE estados_actividades ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE estados_versiones ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE paginas ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE perfiles ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE permisos ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE permisos_perfiles ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE personas ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE proyectos ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE tipos_documentos ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE usuarios ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;
ALTER TABLE versiones ADD COLUMN fecha_eliminacion DATE DEFAULT NULL;