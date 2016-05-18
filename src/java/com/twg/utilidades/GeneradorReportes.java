package com.twg.utilidades;

import com.twg.negocio.ActividadesNegocio;
import com.twg.persistencia.beans.ActividadesBean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * Clase encargada de generar los distintos reportes que se pueden generar en el
 * sistema.
 *
 * @author Andrés Giraldo
 */
public class GeneradorReportes {

    private final ActividadesNegocio actividadesNegocio = new ActividadesNegocio();
    private final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
    public final String rutaReportes = "D://POLITECNICO JIC/PPI SGPTWG/reportes/";

    /**
     * Método encargado de listar los distintos estados de actividades y contar
     * para cada uno de estos el número de actividades que tienen asociados.
     * Entre estos estados no se incluyen aquellos que estén marcados como
     * finalizados en la tabla de estados.
     *
     * @param proyecto Identificador del proyecto que contiene las actividades
     * que se desean consultar.
     * @param version Identificador de la versión que contiene las actividades
     * que se desean consultar.
     * @param persona Identificador de la persona que tiene asociada las
     * actividades que se desean consultar
     * @return El nombre del archivo generado
     */
    public String actividadesPorEstado(Integer proyecto, Integer version, Integer persona) {
        FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
        TextColumnBuilder<String> estado = Columns.column("Estado", "estado", DataTypes.stringType());
        TextColumnBuilder<Integer> cantidad = Columns.column("Actividades", "actividades", DataTypes.integerType());
        TextColumnBuilder<Double> porcentaje = Columns.column("Porcentaje", "porcentaje", DataTypes.percentageType());

        JasperReportBuilder reporte = DynamicReports.report();
        reporte.addColumn(estado);
        reporte.addColumn(cantidad);
        reporte.addColumn(porcentaje);
        reporte.title(Templates.createTitleComponent("Actividades por estado"));
        reporte.pageFooter(Templates.footerComponent);
        reporte.setDataSource(actividadesNegocio.actividadesPorEstado(proyecto, version, persona));
        reporte.summary(cmp.verticalList()
                .add(cmp.verticalGap(20))
                .add(cht.pie3DChart().setTitleFont(boldFont).setKey(estado).series(cht.serie(cantidad))));
        reporte.setTemplate(Templates.reportTemplate);
        return guardarReporte(reporte, "Actividades_por_estado");
    }

    /**
     * Método encargado de generar un reporte con las actividades filtradas en
     * la pantalla de gestión de actividades
     *
     * @param listaActividades
     * @return
     */
    public String listadoActividades(List<ActividadesBean> listaActividades) {
        TextColumnBuilder<String> proyecto = Columns.column("Proyecto", "proyecto", DataTypes.stringType());
        TextColumnBuilder<String> version = Columns.column("Version", "version", DataTypes.stringType());
        TextColumnBuilder<String> actividad = Columns.column("Actividad", "actividad", DataTypes.stringType());
        TextColumnBuilder<String> estado = Columns.column("Estado", "estado", DataTypes.stringType());
        TextColumnBuilder<String> fechaInicio = Columns.column("Fecha de inicio", "fechaInicio", DataTypes.stringType());
        TextColumnBuilder<String> fechaFin = Columns.column("Fecha de fin", "fechaFin", DataTypes.stringType());
        TextColumnBuilder<Double> tiempoEstimado = Columns.column("Tiempo estimado", "tiempoEstimado", DataTypes.doubleType());
        TextColumnBuilder<Double> tiempoInvertido = Columns.column("Tiempo invertido", "tiempoInvertido", DataTypes.doubleType());

        JasperReportBuilder reporte = DynamicReports.report();
        reporte.addColumn(proyecto);
        reporte.addColumn(version);
        reporte.addColumn(actividad);
        reporte.addColumn(estado);
        reporte.addColumn(fechaInicio);
        reporte.addColumn(fechaFin);
        reporte.addColumn(tiempoEstimado);
        reporte.addColumn(tiempoInvertido);
        reporte.title(Templates.createTitleComponent("Listado de actividades"));
        reporte.pageFooter(Templates.footerComponent);
        reporte.setDataSource(actividadesNegocio.listaActividades(listaActividades));
        reporte.setTemplate(Templates.reportTemplate);
        return guardarReporte(reporte, "Listado_de_actividades");
    }

    /**
     * Método encargado de almacenar el reporte generado en la ruta de reportes
     * especificada. La ruta de almacenamiento debe ser previamente definida y
     * seteada en esta clase.
     *
     * @param reporte Corresponde al reporte generado mediante la librería
     * DynamicReports
     * @param nombreReporte Nombre que tendrá el reporte al ser almacenado.
     * @return El nombre del archivo generado
     */
    public String guardarReporte(JasperReportBuilder reporte, String nombreReporte) {
        if (nombreReporte == null) {
            nombreReporte = "";
        }
        try {
            nombreReporte += sdf.format(new Date());
            OutputStream out;
            out = new FileOutputStream(new File(rutaReportes + nombreReporte + ".pdf"));
            reporte.toPdf(out);
            out.flush();
            out.close();
        } catch (IOException | DRException e) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, "Error guardando reporte generado", e);
            return "";
        }
        return nombreReporte + ".pdf";
    }
}
