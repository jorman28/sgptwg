ALTER TABLE proyectos ADD CONSTRAINT proyectos_uk UNIQUE KEY (nombre);
ALTER TABLE versiones ADD CONSTRAINT versiones_uk UNIQUE KEY (proyecto, nombre);