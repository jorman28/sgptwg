package com.twg.reportes;

import com.twg.negocio.ActividadesNegocio;
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

public class GenradorReportes {
    
    private final ActividadesNegocio actividadesNegocio = new ActividadesNegocio();

    public void actividadesPorEstado() {
        FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
        TextColumnBuilder<String> estado = Columns.column("Estado", "estado", DataTypes.stringType());
        TextColumnBuilder<Integer> cantidad = Columns.column("Actividades", "actividades", DataTypes.integerType());
        TextColumnBuilder<Double> porcentaje = Columns.column("Porcentaje", "porcentaje", DataTypes.percentageType());

        JasperReportBuilder report = DynamicReports.report();
        report.addColumn(estado);
        report.addColumn(cantidad);
        report.addColumn(porcentaje);
        report.title(Templates.createTitleComponent("Actividades por estado"));
        report.pageFooter(Templates.footerComponent);
        report.setDataSource(actividadesNegocio.actividadesPorEstado());
        report.summary(cmp.verticalList()
                .add(cmp.verticalGap(20))
                .add(cht.pie3DChart().setTitleFont(boldFont).setKey(estado).series(cht.serie(cantidad))));
        report.setTemplate(Templates.reportTemplate);
        try {
//            report.toXlsx(new FileOutputStream(new File("D://POLITECNICO JIC/PPI SGPTWG/sgptwg/logs/reporte.xlsx")));
            report.show();
        } catch (DRException ex) {
            Logger.getLogger(GenradorReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new GenradorReportes().actividadesPorEstado();
    }

}
