DROP DATABASE IF EXISTS sgptwg;
CREATE DATABASE sgptwg DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;
USE sgptwg;

CREATE TABLE tipos_documentos(
	tipo VARCHAR(3) NOT NULL,
	nombre VARCHAR(30) NOT NULL,
	CONSTRAINT tipos_documentos_pk PRIMARY KEY (tipo)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE personas(
	id INTEGER NOT NULL AUTO_INCREMENT,
	documento VARCHAR(15) NOT NULL,
	tipo_documento VARCHAR(3) NOT NULL,
	nombres VARCHAR(50) NOT NULL,
	apellidos VARCHAR(50) NOT NULL,
	direccion VARCHAR(50) NOT NULL,
	telefono VARCHAR(15) NOT NULL,
	celular VARCHAR(15),
	correo VARCHAR(50),
	CONSTRAINT personas_pk PRIMARY KEY (id),
	CONSTRAINT personas_tipos_documentos_fk FOREIGN KEY (tipo_documento) REFERENCES tipos_documentos (tipo),
	CONSTRAINT documentos_personas_uk UNIQUE KEY (documento, tipo_documento)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE perfiles(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	CONSTRAINT pefiles_pk PRIMARY KEY (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE usuarios(
	id_persona INTEGER NOT NULL,
	usuario VARCHAR(15) NOT NULL,
	clave VARCHAR(15) NOT NULL,
	perfil INTEGER NOT NULL,
	CONSTRAINT usuarios_pk PRIMARY KEY (id_persona),
	CONSTRAINT usuarios_uk UNIQUE KEY (usuario),
	CONSTRAINT usuarios_perfiles_pk FOREIGN KEY (perfil) REFERENCES perfiles (id),
	CONSTRAINT usuarios_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE clientes(
	id_persona INTEGER NOT NULL,
	fecha_inicio DATE NOT NULL,
	CONSTRAINT clientes_pk PRIMARY KEY (id_persona),
	CONSTRAINT clientes_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE cargos(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	CONSTRAINT cargos_pk PRIMARY KEY (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE empleados(
	id_persona INTEGER NOT NULL,
	cargo INTEGER NOT NULL,
	CONSTRAINT empleados_pk PRIMARY KEY (id_persona),
	CONSTRAINT empleados_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id),
	CONSTRAINT empleados_cargos_fk FOREIGN KEY (cargo) REFERENCES cargos (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE proyectos(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(30) NOT NULL,
	fecha_inicio DATE NOT NULL,
	id_persona INTEGER NOT NULL,
	CONSTRAINT proyectos_pk PRIMARY KEY (id),
	CONSTRAINT proyectos_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE estados_versiones(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(30) NOT NULL,
	CONSTRAINT estados_versiones_pk PRIMARY KEY (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE versiones(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(30) NOT NULL,
	fecha_inicio DATE NOT NULL,
	fecha_terminacion DATE NOT NULL,
	alcance VARCHAR(1000) NOT NULL,
	proyecto INTEGER NOT NULL,
	estado INTEGER NOT NULL,
	CONSTRAINT versiones_pk PRIMARY KEY (id),
	CONSTRAINT veriones_proyectos_fk FOREIGN KEY (proyecto) REFERENCES proyectos (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE personas_versiones(
	id_persona INTEGER NOT NULL,
	version INTEGER NOT NULL,
	CONSTRAINT personas_versiones_pk PRIMARY KEY (id_persona, version),
	CONSTRAINT personas_versiones_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id),
	CONSTRAINT personas_versiones_versiones_fk FOREIGN KEY (version) REFERENCES versiones (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE estados_actividades(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(30) NOT NULL,
	CONSTRAINT estados_actividades_pk PRIMARY KEY (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE actividades(
	id INTEGER NOT NULL AUTO_INCREMENT,
	version INTEGER NOT NULL,
	descripcion VARCHAR(1000) NOT NULL,
	fecha_estimada_inicio DATE NOT NULL,
	fecha_estimada_terminacion DATE NOT NULL,
	fecha_real_inicio DATE NOT NULL,
	fecha_real_terminacion DATE NOT NULL,
	tiempo_estimado TIME NOT NULL,
	tiempo_invertido TIME NOT NULL,
	estado INTEGER NOT NULL,
	CONSTRAINT actividades_pk PRIMARY KEY (id),
	CONSTRAINT actividades_versiones_fk FOREIGN KEY (version) REFERENCES versiones (id),
	CONSTRAINT actividades_estados_fk FOREIGN KEY (version) REFERENCES estados_actividades (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE archivos(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	descripcion VARCHAR(1000),
	fecha_creacion DATE NOT NULL,
	ruta VARCHAR(200) NOT NULL,
	id_persona INTEGER NOT NULL,
	tipo VARCHAR(50) NOT NULL,
	CONSTRAINT documentos_cargados_pk PRIMARY KEY (id),
	CONSTRAINT documentos_cargados_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE comentarios(
	id INTEGER NOT NULL AUTO_INCREMENT,
	id_persona INTEGER NOT NULL,
	comentario VARCHAR(1000) NOT NULL,
	fecha_creacion DATE NOT NULL,
	CONSTRAINT comentarios_pk PRIMARY KEY (id),
	CONSTRAINT comentarios_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE comentarios_archivos(
	comentario INTEGER NOT NULL,
	archivo INTEGER NOT NULL,
	CONSTRAINT comentarios_archivos_pk PRIMARY KEY (comentario, archivo),
	CONSTRAINT comentarios_archivos_comentarios_fk FOREIGN KEY (comentario) REFERENCES comentarios (id),
	CONSTRAINT comentarios_archivos_archivos_fk FOREIGN KEY (archivo) REFERENCES archivos (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE comentarios_referenciados(
	comentario INTEGER NOT NULL,
	id_persona INTEGER NOT NULL,
	CONSTRAINT comentarios_referenciados_pk PRIMARY KEY (comentario, id_persona),
	CONSTRAINT comentarios_referenciados_comentarios_fk FOREIGN KEY (comentario) REFERENCES comentarios (id),
	CONSTRAINT comentarios_referenciados_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE auditorias(
	id INTEGER NOT NULL AUTO_INCREMENT,
	id_persona INTEGER NOT NULL,
	fecha_creacion DATE NOT NULL,
	accion VARCHAR(80) NOT NULL,
	descripcion VARCHAR(1000) NOT NULL,
	CONSTRAINT auditorias_pk PRIMARY KEY (id),
	CONSTRAINT auditorias_personas_fk FOREIGN KEY (id_persona) REFERENCES personas (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE paginas(
	id INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	url VARCHAR(100) NOT NULL,
	CONSTRAINT paginas_pk PRIMARY KEY (id),
	CONSTRAINT paginas_uk UNIQUE KEY (nombre)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE permisos_paginas(
	id INTEGER NOT NULL AUTO_INCREMENT,
	pagina INTEGER NOT NULL,
	permiso VARCHAR(50) NOT NULL,
	CONSTRAINT permisos_paginas_pk PRIMARY KEY (id),
	CONSTRAINT permisos_paginas_uk UNIQUE KEY (pagina, permiso),
	CONSTRAINT permisos_paginas_fk FOREIGN KEY (pagina) REFERENCES paginas (id)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;

CREATE TABLE permisos_perfiles(
	permiso INTEGER NOT NULL,
	perfil INTEGER NOT NULL,
	CONSTRAINT permisos_perfiles_pk PRIMARY KEY (permiso, perfil)
) ENGINE = InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_spanish_ci;