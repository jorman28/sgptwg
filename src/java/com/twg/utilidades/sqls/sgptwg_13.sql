INSERT INTO paginas (id, nombre, url, grupo) VALUES (14, 'Actividades por estado', '/ActividadesPorEstadoController', 13);
INSERT INTO permisos (id,pagina,permiso) VALUES (45,14,'ACCESO');
INSERT INTO permisos_perfiles (permiso, perfil) VALUES (45, 1);