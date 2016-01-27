package br.com.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import br.com.bean.ConexaoMongo;
import br.com.entities.Venda;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class VendasService {

	

	/**function to insert the data to the database */
	public boolean insertVenda(Venda vendas) throws UnknownHostException {
	
	/**Connecting to MongoDB*/
	DBCollection table = ConexaoMongo.getConnection("vendas");
		
	
	/**creating a document to store as key and value*/
			
			BasicDBObject document = new BasicDBObject();
			
			
			document.put("codigocliente", vendas.getCodigoCliente());
			document.put("nomecliente", vendas.getNomeCliente());
			document.put("codigoproduto", vendas.getCodigoProduto());
			document.put("descricaoproduto", vendas.getDescricaoProduto());
			document.put("quantidade", vendas.getQuantidade());
		
			
			
			
			/** inserting to the document to collection or table*/
			if(table.insert(document) != null) {
				
				return true;
				
			}

			return false;
			

			
	}
	

	
	public  List<Venda> buscarVendas(Venda ven) throws UnknownHostException {

	
	DBCollection table= ConexaoMongo.getConnection("vendas");
			
	BasicDBObject searchQuery = new BasicDBObject();
	
	if(ven.getCodigoCliente() != null) {
		
		searchQuery.put("codigocliente", ven.getCodigoCliente());
		
	}
	
	if(ven.getCodigoProduto() != null) {
		
		searchQuery.put("codigoproduto", ven.getCodigoProduto());
	}
	
	
	DBCursor cursor = table.find(searchQuery);
	List<Venda>list= new ArrayList<Venda>();


	while (cursor.hasNext()) {
				
	DBObject obj= cursor.next();


	Map map= VendasService.converteDocumentoParaMap(obj.toString());

	Set<Entry<String,String>> set=	map.entrySet();

	Iterator<Entry<String,String>> itr= set.iterator();
	Venda vendas = new Venda();
	  
	  while(itr.hasNext()){
		
		    	Entry<String, String> entry = itr.next();
		    	String key=entry.getKey();

		    	key = key.substring(2,key.length()-2);
		    	
	           String value=entry.getValue();
			    	
			if(key.equalsIgnoreCase("codigo")){
	    	
				vendas.setCodigo(value.substring(2,value.length()-2));
			
			} else if(key.equalsIgnoreCase("codigocliente")){
			
				vendas.setCodigoCliente(value.substring(2,value.length()-2));
			
			} else if(key.equalsIgnoreCase("nomecliente")){
			
				vendas.setNomeCliente(value.substring(2,value.length()-2));
			
			} else if(key.equalsIgnoreCase("codigoproduto")){
			
				vendas.setCodigoProduto(value.substring(2,value.length()-2));
			
			} else if(key.equalsIgnoreCase("descricaoproduto")){
			
				vendas.setDescricaoProduto(value.substring(2,value.length()-2));
			
			} else if(key.equalsIgnoreCase("quantidade")){
			
				vendas.setQuantidade(value.substring(2,value.length()-2));
			
			}
	    }     
	  
	   list.add(vendas);
	}  
			return list;
	}
 
		public static Map<String,String> converteDocumentoParaMap (String s){
			s= s.substring(1,s.length()-1);
			String sArr[]= s.split(",");
			Map<String,String> map = new LinkedHashMap<String,String>();
			for(int i=1;i<sArr.length;i++){
				if(!sArr[i].contains("$date")){
				String keyValue[]= sArr[i].split(":");
				map.put(keyValue[0],keyValue[1]);
				System.out.println(keyValue[0]+","+keyValue[1]);
				}else{
					String keyValue[]= sArr[i].split(":");
					map.put(keyValue[0],keyValue[2]);
				}
			}
			return map;
		}
	

	}