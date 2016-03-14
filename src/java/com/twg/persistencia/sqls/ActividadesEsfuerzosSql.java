package com.twg.persistencia.sqls;

/**
 *
 * @author Jorman
 */
public class ActividadesEsfuerzosSql {

    public String contarActividades_Esfuerzos() {
        return "SELECT COUNT(*) FROM actividades_esfuerzos";
    }

    public String consultarActividades_Esfuerzos(Integer id) {
        String sql = "SELECT * FROM actividades_esfuerzos WHERE fecha_eliminacion IS NULL ";
        if (id != null) {
            sql += "AND id = " + id + " ";
        }
        return sql;
    }

    public String insertarActividad_Esfuerzo() {
        return "INSERT INTO actividades_esfuerzos (actividad, empleado, fecha, tiempo, descripcion) VALUES (?, ?, ?, ?, ?)";
    }

    public String actualizarActividad_Esfuerzo() {
        return "UPDATE actividades_esfuerzos SET actividad = ?, empleado = ?, fecha = ?, tiempo = ?, descripcion = ? WHERE id = ?";
    }

    public String eliminarActividad_Esfuerzo() {
        return "UPDATE actividades_esfuerzos SET fecha_eliminacion = now() WHERE actividad = ?";
    }
}
