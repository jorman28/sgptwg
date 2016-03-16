
ALTER TABLE `actividades` CHANGE `fecha_real_inicio` `fecha_real_inicio` DATE NULL;
ALTER TABLE `actividades` CHANGE `fecha_real_terminacion` `fecha_real_terminacion` DATE NULL;
ALTER TABLE `actividades` CHANGE `tiempo_invertido` `tiempo_invertido` TIME NULL;

ALTER TABLE comentarios MODIFY fecha_creacion DATETIME DEFAULT NULL;

DELETE pxp FROM
    paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id
        INNER JOIN
    permisos_perfiles pxp ON pxp.permiso = per.id WHERE pag.nombre = "Ayuda";
    
DELETE per FROM paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id 
WHERE
    pag.nombre = 'Ayuda';
    
DELETE FROM paginas 
WHERE
    nombre = 'Ayuda';