package com.twg.reportes;

import com.twg.negocio.ActividadesNegocio;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private final String rutaReportes = "D://POLITECNICO JIC/PPI SGPTWG/reportes/";

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
     * @param tipoReporte Indica el formato en el que se generará el reporte
     */
    public void actividadesPorEstado(Integer proyecto, Integer version, Integer persona, String tipoReporte) {
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
        guardarReporte(reporte, tipoReporte, "Actividades_por_estado");
    }

    /**
     * Método encargado de almacenar el reporte generado en la ruta de reportes
     * especificada. La ruta de almacenamiento debe ser previamente definida y
     * seteada en esta clase.
     *
     * @param reporte Corresponde al reporte generado mediante la librería
     * DynamicReports
     * @param tipoReporte Si es pdf, xlsx o csv.
     * @param nombreReporte Nombre que tendrá el reporte al ser almacenado.
     */
    public void guardarReporte(JasperReportBuilder reporte, String tipoReporte, String nombreReporte) {
        if (tipoReporte == null
                || (!tipoReporte.equalsIgnoreCase("pdf") && !tipoReporte.equalsIgnoreCase("xlsx") && !tipoReporte.equalsIgnoreCase("csv"))) {
            tipoReporte = "pdf";
        }
        if (nombreReporte == null) {
            nombreReporte = "";
        }
        try {
            nombreReporte += sdf.format(new Date());
            OutputStream out;
            out = new FileOutputStream(new File(rutaReportes + nombreReporte + "." + tipoReporte));
            switch (tipoReporte.toLowerCase()) {
                case "xslx":
                    reporte.toXlsx(out);
                    break;
                case "csv":
                    reporte.toCsv(out);
                    break;
                default:
                    reporte.toPdf(out);
                    break;
            }
            out.flush();
            out.close();
        } catch (IOException | DRException  e) {
            Logger.getLogger(ActividadesNegocio.class.getName()).log(Level.SEVERE, "Error guardando reporte generado", e);
        }
    }

    public static void main(String[] args) {
        new GeneradorReportes().actividadesPorEstado(null, null, null, null);
    }

}
