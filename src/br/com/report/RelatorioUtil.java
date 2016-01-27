package br.com.report;

import java.io.*;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import com.jaspersoft.mongodb.connection.MongoDbConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;

public class RelatorioUtil {
	
	public static OutputStream createPDFReport(InputStream inputStream, 
											   Map<String, Object> parametros,
					           				   MongoDbConnection conexao,
					           				   HttpServletResponse response,
					           				   String nomeRelatorio) throws JRException, IOException {
 
        // configura o content type do response
         response.setContentType( "application/pdf");

        // For�ando o download
         response.setHeader("Content-Disposition", "attachment; filename=" + nomeRelatorio);
        
        // obt�m o OutputStream para escrever o relat�rio
        OutputStream out = response.getOutputStream();
 
        //Cria um JasperPrint, que � a vers�o preenchida do relat�rio usando uma conex�o.
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, conexao);
 
        // Exporta em PDF, escrevendo os dados no output stream do response.
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter( JRExporterParameter.JASPER_PRINT, jasperPrint );
        exporter.setParameter( JRExporterParameter.OUTPUT_STREAM, out );
 
        // gera o relat�rio
        exporter.exportReport();
 
        // retorna o OutputStream
        return out;
 
    }
	
}
