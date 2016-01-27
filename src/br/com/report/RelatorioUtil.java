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

        // Forçando o download
         response.setHeader("Content-Disposition", "attachment; filename=" + nomeRelatorio);
        
        // obtém o OutputStream para escrever o relatório
        OutputStream out = response.getOutputStream();
 
        //Cria um JasperPrint, que é a versão preenchida do relatório usando uma conexão.
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, conexao);
 
        // Exporta em PDF, escrevendo os dados no output stream do response.
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter( JRExporterParameter.JASPER_PRINT, jasperPrint );
        exporter.setParameter( JRExporterParameter.OUTPUT_STREAM, out );
 
        // gera o relatório
        exporter.exportReport();
 
        // retorna o OutputStream
        return out;
 
    }
	
}
