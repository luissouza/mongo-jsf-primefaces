package br.com.report;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jaspersoft.mongodb.MongoDbDataSource;
import com.jaspersoft.mongodb.connection.MongoDbConnection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/relatorioServlet")
public class RelatorioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RelatorioServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String caminhoRelatorioJasper = request.getSession().getServletContext().getRealPath("resources")
				+ File.separator + "relatorio.jasper";
		String caminhoRelatorioJrxml = request.getSession().getServletContext().getRealPath("resources")
				+ File.separator + "relatorio.jrxml";

		String nomeRelatorio = "relatorio.pdf";

		// compila o relatorio em tempo de execução
		try {
			JasperCompileManager.compileReportToFile(caminhoRelatorioJrxml,
					caminhoRelatorioJasper);
		} catch (JRException e2) {
			e2.printStackTrace();
		}

		InputStream inputStream = new FileInputStream(caminhoRelatorioJasper);

		Map<String, Object> parametros = new HashMap<String, Object>();

		String mongoURI = "mongodb://127.0.0.1/test";
		MongoDbConnection connection = null;

		try {
			connection = new MongoDbConnection(mongoURI, null, null);

			final String codigo = request.getParameter("pCodigoCliente");
			parametros.put(MongoDbDataSource.CONNECTION, connection);
			parametros.put("pCodigoCliente", codigo);

			RelatorioUtil.createPDFReport(inputStream, parametros, connection,
					response, nomeRelatorio);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
