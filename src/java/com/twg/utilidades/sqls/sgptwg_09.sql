UPDATE permisos SET permiso = 'ACCESO';

INSERT INTO permisos (id, pagina, permiso) VALUES (17, (SELECT id FROM paginas WHERE nombre = 'Usuarios'), 'CONSULTAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (18, (SELECT id FROM paginas WHERE nombre = 'Usuarios'), 'ELIMINAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (19, (SELECT id FROM paginas WHERE nombre = 'Usuarios'), 'GUARDAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (20, (SELECT id FROM paginas WHERE nombre = 'Permisos'), 'CONSULTAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (21, (SELECT id FROM paginas WHERE nombre = 'Permisos'), 'ELIMINAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (22, (SELECT id FROM paginas WHERE nombre = 'Permisos'), 'PERMISOS');
INSERT INTO permisos (id, pagina, permiso) VALUES (23, (SELECT id FROM paginas WHERE nombre = 'Permisos'), 'GUARDAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (24, (SELECT id FROM paginas WHERE nombre = 'Personas'), 'CONSULTAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (25, (SELECT id FROM paginas WHERE nombre = 'Personas'), 'CREAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (26, (SELECT id FROM paginas WHERE nombre = 'Personas'), 'ELIMINAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (27, (SELECT id FROM paginas WHERE nombre = 'Personas'), 'GUARDAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (28, (SELECT id FROM paginas WHERE nombre = 'Cargos'), 'ELIMINAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (29, (SELECT id FROM paginas WHERE nombre = 'Cargos'), 'GUARDAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (30, (SELECT id FROM paginas WHERE nombre = 'Estados'), 'ELIMINAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (31, (SELECT id FROM paginas WHERE nombre = 'Estados'), 'GUARDAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (32, (SELECT id FROM paginas WHERE nombre = 'Versiones'), 'CONSULTAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (33, (SELECT id FROM paginas WHERE nombre = 'Versiones'), 'CREAR_PROYECTO');
INSERT INTO permisos (id, pagina, permiso) VALUES (34, (SELECT id FROM paginas WHERE nombre = 'Versiones'), 'GUARDAR_PROYECTO');
INSERT INTO permisos (id, pagina, permiso) VALUES (35, (SELECT id FROM paginas WHERE nombre = 'Versiones'), 'ELIMINAR_PROYECTO');
INSERT INTO permisos (id, pagina, permiso) VALUES (36, (SELECT id FROM paginas WHERE nombre = 'Versiones'), 'CREAR_VERSION');
INSERT INTO permisos (id, pagina, permiso) VALUES (37, (SELECT id FROM paginas WHERE nombre = 'Versiones'), 'GUARDAR_VERSION');
INSERT INTO permisos (id, pagina, permiso) VALUES (38, (SELECT id FROM paginas WHERE nombre = 'Versiones'), 'ELIMINAR_VERSION');
INSERT INTO permisos (id, pagina, permiso) VALUES (39, (SELECT id FROM paginas WHERE nombre = 'Versiones'), 'COMENTAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (40, (SELECT id FROM paginas WHERE nombre = 'Actividades'), 'CONSULTAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (41, (SELECT id FROM paginas WHERE nombre = 'Actividades'), 'CREAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (42, (SELECT id FROM paginas WHERE nombre = 'Actividades'), 'ELIMINAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (43, (SELECT id FROM paginas WHERE nombre = 'Actividades'), 'GUARDAR');
INSERT INTO permisos (id, pagina, permiso) VALUES (44, (SELECT id FROM paginas WHERE nombre = 'Actividades'), 'COMENTAR');

INSERT INTO permisos_perfiles (permiso, perfil) 
SELECT 
    per.id, 1
FROM
    permisos per
        LEFT JOIN
    permisos_perfiles perper ON per.id = perper.permiso
WHERE
    perper.permiso IS NULL