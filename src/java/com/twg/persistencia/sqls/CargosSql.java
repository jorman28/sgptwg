package com.twg.persistencia.sqls;

/**
 * Esta clase define métodos para contruír los SQLs utilizados en el DAO.
 *
 * @author Andrés Felipe Giraldo, Jorman Rincón, Erika Jhoana Castaneda
 */
public class CargosSql {

    /**
     * Constructor de la clase
     */
    public CargosSql() {
    }

    /**
     * Método encargado de retornar el SQL para consultar los cargos, filtrando
     * por uno en especial o por todos.
     *
     * @param nombre
     * @param nombreExacto
     * @param limite
     * @return
     */
    public String consultarCargos(String nombre, boolean nombreExacto, String limite) {
        String sql = "SELECT id, nombre FROM cargos WHERE 1 = 1 AND fecha_eliminacion IS NULL ";
        if (nombre != null && !nombre.isEmpty()) {
            if (nombreExacto) {
                sql += "AND nombre = '" + nombre + "'";
            } else {
                sql += "AND nombre like '%" + nombre + "%'";
            }
        }
        if (limite != null && !limite.isEmpty()) {
            sql += " LIMIT " + limite + " ";
        }
        return sql;
    }

    /**
     * Método encargado de retornar el SQL para consultar la cantidad total de los cargos, filtrando
     * por uno en especial o por todos.
     *
     * @param nombre
     * @param nombreExacto
     * @return
     */
    public String cantidadCargos(String nombre, boolean nombreExacto) {
        String sql = "SELECT COUNT(*) AS cantidadCargos FROM cargos WHERE 1 = 1 AND fecha_eliminacion IS NULL ";
        if (nombre != null && !nombre.isEmpty()) {
            if (nombreExacto) {
                sql += "AND nombre = '" + nombre + "'";
            } else {
                sql += "AND nombre like '%" + nombre + "%'";
            }
        }
        return sql;
    }
    
    /**
     * Método encargado de retornar el SQL para consultar un cargo específico.
     *
     * @param id
     * @return
     */
    public String consultarCargo(int id) {
        return "SELECT id, nombre FROM cargos WHERE id = " + id;
    }

    /**
     * Método encargado de retornar el SQL para insertar un cargo nuevo.
     *
     * @return
     */
    public String insertarCargo() {
        return "INSERT INTO cargos (nombre) VALUES (?)";
    }

    /**
     * Método encargado de retornar el SQL para actualizar un cargo existente.
     *
     * @return
     */
    public String actualizarCargo() {
        return "UPDATE cargos SET nombre = ? WHERE id = ?";
    }

    /**
     * Método encargado de retornar el SQL para eliminar lógicamente un cargo,
     * actualizando la fecha de eliminación con la fecha actual.
     *
     * @return
     */
    public String eliminarCargo() {
        return "UPDATE cargos SET fecha_eliminacion = now() WHERE id = ?";
    }
}
