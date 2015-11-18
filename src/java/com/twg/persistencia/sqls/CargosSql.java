package com.twg.persistencia.sqls;

/**
 *
 * @author Erika Jhoana
 */
public class CargosSql {

    public CargosSql() {
    }

    public String consultarCargos(String nombre, boolean nombreExacto) {
        String sql = "SELECT id, nombre FROM cargos WHERE 1 = 1 AND fecha_eliminacion IS NULL ";
        if (nombre != null && !nombre.isEmpty()) {
            if (nombreExacto) {
                sql += "AND nombre = '" + nombre + "'";
            } else {
                sql += "AND nombre like '%" + nombre + "%'";
            }
        }
        return sql;
    }

    public String consultarCargo(int id) {
        return "SELECT id, nombre FROM cargos WHERE id = " + id;
    }

    public String insertarCargo() {
        return "INSERT INTO cargos (nombre) VALUES (?)";
    }

    public String actualizarCargo() {
        return "UPDATE cargos SET nombre = ? WHERE id = ?";
    }

    public String eliminarCargo() {
        return "UPDATE cargos SET fecha_eliminacion = now() WHERE id = ?";
    }
}
