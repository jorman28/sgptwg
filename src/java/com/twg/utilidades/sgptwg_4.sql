ALTER TABLE actividades_empleados DROP FOREIGN KEY actividades_empleados_empleados_fk;
ALTER TABLE actividades_esfuerzos DROP FOREIGN KEY actividades_esfuerzos_empleados_fk;
ALTER TABLE actividades_empleados ADD CONSTRAINT actividades_empleados_empleados_fk FOREIGN KEY (empleado) REFERENCES personas (id);
ALTER TABLE actividades_esfuerzos ADD CONSTRAINT actividades_esfuerzos_empleados_fk FOREIGN KEY (empleado) REFERENCES personas (id);

DROP TABLE empleados;
DROP TABLE clientes;

ALTER TABLE personas ADD COLUMN cargo INTEGER NOT NULL;
INSERT INTO cargos (id, nombre) VALUES ('5', 'CLIENTE');
UPDATE personas SET cargo = 5;
ALTER TABLE personas ADD CONSTRAINT personas_cargos_fk FOREIGN KEY (cargo) REFERENCES cargos (id);

ALTER TABLE comentarios ADD COLUMN tipo_destino VARCHAR (40) NOT NULL;
ALTER TABLE comentarios ADD COLUMN id_destino INTEGER NOT NULL;

ALTER TABLE estados_actividades ADD COLUMN tipo_estado VARCHAR(40) NOT NULL;
RENAME TABLE estados_actividades TO estados;
DROP TABLE estados_versiones;

ALTER TABLE proyectos DROP FOREIGN KEY proyectos_personas_fk;
ALTER TABLE proyectos DROP COLUMN id_persona;

CREATE TABLE IF NOT EXISTS personas_proyectos (
  id_persona INTEGER NOT NULL,
  id_proyecto INTEGER NOT NULL,
  CONSTRAINT personas_proyectos_pk PRIMARY KEY (id_persona, id_proyecto),
  CONSTRAINT personas_proyectos_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id),
  CONSTRAINT personas_proyectos_proyectos_fk FOREIGN KEY (id_proyecto) REFERENCES proyectos (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;