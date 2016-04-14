DELETE pxp FROM
    paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id
        INNER JOIN
    permisos_perfiles pxp ON pxp.permiso = per.id WHERE pag.nombre = 'Cerrar sesión';
    
DELETE per FROM paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id 
WHERE
    pag.nombre = 'Cerrar sesión';
    
DELETE FROM paginas 
WHERE
    nombre = 'Cerrar sesión';
    
DELETE pxp FROM
    paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id
        INNER JOIN
    permisos_perfiles pxp ON pxp.permiso = per.id WHERE pag.nombre = 'Continuar';
    
DELETE per FROM paginas pag
        INNER JOIN
    permisos per ON per.pagina = pag.id 
WHERE
    pag.nombre = 'Continuar';
    
DELETE FROM paginas 
WHERE
    nombre = 'Continuar';

UPDATE paginas SET url='/PermisosController' WHERE id='4';
UPDATE paginas SET url='/CargosController' WHERE id='7';
UPDATE paginas SET url='/EstadosController' WHERE id='8';
UPDATE paginas SET url='/ProyectosController' WHERE id='10';
UPDATE paginas SET url='/ActividadesController' WHERE id='11';