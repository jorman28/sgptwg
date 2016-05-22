UPDATE paginas SET url = '/InicioController' WHERE nombre = 'Inicio';

DELETE pxp FROM
    paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id
        INNER JOIN
    permisos_perfiles pxp ON pxp.permiso = per.id WHERE pag.nombre = 'Actividades por estado';
    
DELETE per FROM paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id 
WHERE
    pag.nombre = 'Actividades por estado';
    
DELETE FROM paginas 
WHERE
    nombre = 'Actividades por estado';

DELETE pxp FROM
    paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id
        INNER JOIN
    permisos_perfiles pxp ON pxp.permiso = per.id WHERE pag.nombre = 'Reportes';
    
DELETE per FROM paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id 
WHERE
    pag.nombre = 'Reportes';
    
DELETE FROM paginas 
WHERE
    nombre = 'Reportes';