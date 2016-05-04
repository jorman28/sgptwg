INSERT INTO `paginas` (`id`, `nombre`, `url`, `grupo`, `fecha_eliminacion`) 
VALUES (15, 'Auditorias', '/AuditoriasController', '2', NULL);

INSERT INTO `permisos`(`id`, `pagina`, `permiso`) 
VALUES (46,15,'ELIMINAR'),
(47,15,'CONSULTAR');

INSERT INTO `permisos_perfiles` (`permiso`, `perfil`) VALUES ('46', '1'), ('47', '1');