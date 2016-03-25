INSERT INTO `sgptwg`.`tipos_documentos` (`tipo`, `nombre`) VALUES ('CC', 'Cédula de ciudadanía');
INSERT INTO `sgptwg`.`tipos_documentos` (`tipo`, `nombre`) VALUES ('TI', 'Tarjeta de identidad');
INSERT INTO `sgptwg`.`tipos_documentos` (`tipo`, `nombre`) VALUES ('CE', 'Cédula de extranjería');
INSERT INTO `sgptwg`.`tipos_documentos` (`tipo`, `nombre`) VALUES ('NIT', 'Número de Identificación Tributaria');
INSERT INTO `sgptwg`.`tipos_documentos` (`tipo`, `nombre`) VALUES ('PAS', 'Pasaporte');

INSERT INTO `sgptwg`.`perfiles` (`id`, `nombre`) VALUES ('1', 'ADMINISTRADOR');
INSERT INTO `sgptwg`.`perfiles` (`id`, `nombre`) VALUES ('2', 'EMPLEADO');
INSERT INTO `sgptwg`.`perfiles` (`id`, `nombre`) VALUES ('3', 'CLIENTE');

INSERT INTO `sgptwg`.`personas` (`id`, `documento`, `tipo_documento`, `nombres`, `apellidos`, `direccion`, `telefono`, `celular`, `correo`) VALUES ('1', '1128460258', 'CC', 'Andrés Felipe', 'Giraldo Grajales', 'Calle 39 SUR # 84 B 28', '3373789', '3122888141', 'andres_giraldo24131@elpoli.edu.co');
INSERT INTO `sgptwg`.`personas` (`id`, `documento`, `tipo_documento`, `nombres`, `apellidos`, `direccion`, `telefono`, `celular`, `correo`) VALUES ('2', '70853231', 'CC', 'Ferdinand', 'Giraldo Muñoz', 'Calle 39 SUR # 84 B 28', '3353789', '3209812103', 'ferdi-010@hotmail.com');
INSERT INTO `sgptwg`.`personas` (`id`, `documento`, `tipo_documento`, `nombres`, `apellidos`, `direccion`, `telefono`, `celular`, `correo`) VALUES ('3', '43447431', 'CC', 'Nidia Patricia', 'Grajales Vergara', 'Calle 39 SUR # 84 B 28', '3142397', '3215569070', 'nidiap_g@gmail.com');
INSERT INTO `sgptwg`.`personas` (`id`, `documento`, `tipo_documento`, `nombres`, `apellidos`, `direccion`, `telefono`, `celular`, `correo`) VALUES ('4', '1128460659', 'CC', 'Sulany', 'Ceballos Betancur', 'Calle 44 SUR 83 C 13', '3356271', '312886512', 'sulany_ceballos35141@elpoli.edu.co');

INSERT INTO `sgptwg`.`cargos` (`id`,`nombre`) VALUES ('1','GERENTE DE PROYECTOS');
INSERT INTO `sgptwg`.`cargos` (`id`,`nombre`) VALUES ('2','DISEÑADOR');
INSERT INTO `sgptwg`.`cargos` (`id`,`nombre`) VALUES ('3','DESARROLLADOR');
INSERT INTO `sgptwg`.`cargos` (`id`,`nombre`) VALUES ('4','TESTER');

ALTER TABLE paginas ADD COLUMN grupo INTEGER;
ALTER TABLE paginas MODIFY COLUMN url VARCHAR(100) COLLATE latin1_spanish_ci DEFAULT NULL;

INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`, `url`) VALUES ('1', 'Inicio', '/PaginaInicioController');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`) VALUES ('2', 'Seguridad');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`, `url`, `grupo`) VALUES ('3', 'Usuarios', '/UsuariosController', '2');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`, `grupo`) VALUES ('4', 'Permisos', '2');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`) VALUES ('5', 'Configuración');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`, `url`, `grupo`) VALUES ('6', 'Personas', '/PersonasController', '5');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`, `grupo`) VALUES ('7', 'Cargos', '5');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`, `grupo`) VALUES ('8', 'Estados', '5');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`) VALUES ('9', 'Proyectos');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`, `grupo`) VALUES ('10', 'Versiones', '9');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`, `grupo`) VALUES ('11', 'Actividades', '9');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`) VALUES ('12', 'Documentación');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`) VALUES ('13', 'Reportes');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`) VALUES ('14', 'Ayuda');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`) VALUES ('15', 'Cerrar sesión');
INSERT INTO `sgptwg`.`paginas` (`id`, `nombre`, `url`, `grupo`) VALUES ('16', 'Continuar', '/InicioSesionController', '15');

DROP TABLE personas_versiones;

CREATE TABLE actividades_empleados(
	actividad INTEGER NOT NULL,
	empleado INTEGER NOT NULL,
	CONSTRAINT actividades_empleados_pk PRIMARY KEY (actividad, empleado),
	CONSTRAINT actividades_empleados_actividades_fk FOREIGN KEY (actividad) REFERENCES actividades (id),
	CONSTRAINT actividades_empleados_empleados_fk FOREIGN KEY (empleado) REFERENCES empleados (id_persona)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

ALTER TABLE permisos_paginas RENAME permisos;

ALTER TABLE permisos_perfiles ADD CONSTRAINT permisos_perfiles_permisos_fk FOREIGN KEY (permiso) REFERENCES permisos (id);
ALTER TABLE permisos_perfiles ADD CONSTRAINT permisos_perfiles_perfiles_fk FOREIGN KEY (perfil) REFERENCES perfiles (id);

CREATE TABLE actividades_esfuerzos (
	id INTEGER NOT NULL AUTO_INCREMENT,
	actividad INTEGER NOT NULL,
	empleado INTEGER NOT NULL,
	fecha DATE NOT NULL,
	tiempo TIME NOT NULL,
	descripcion VARCHAR(500) NOT NULL,
	CONSTRAINT actividades_esfuerzos_pk PRIMARY KEY (id),
	CONSTRAINT actividades_esfuerzos_actividades_fk FOREIGN KEY (actividad) REFERENCES actividades (id),
	CONSTRAINT actividades_esfuerzos_empleados_fk FOREIGN KEY (empleado) REFERENCES empleados (id_persona)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

INSERT INTO permisos (pagina, permiso) SELECT id, 'TODOS' FROM paginas;

INSERT INTO permisos_perfiles (permiso, perfil) SELECT id, 1 FROM permisos;