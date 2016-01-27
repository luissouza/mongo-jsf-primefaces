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
import br.com.entities.Cliente;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class ClientesService {

public static DBCollection getConnection(String nomeDB, String nomeCollection) throws UnknownHostException {

		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB(nomeDB);
		DBCollection table = db.getCollection(nomeCollection);
		return table;
	}

	public boolean insertCliente( Cliente clientes) throws UnknownHostException {
	
	DBCollection table = ConexaoMongo.getConnection("clientes");

			BasicDBObject document = new BasicDBObject();
			document.put("nome", clientes.getNome());
			document.put("cpf", clientes.getCpf());
			document.put("rg", clientes.getRg());
			document.put("endereco", clientes.getEndereco());
			document.put("numero", clientes.getNumero());

			if(table.insert(document) != null) {
				return true;
				
			}

			return false;
			
	}
	
	public List<Cliente> buscarClientes(Cliente cli) throws UnknownHostException {

		
		DBCollection table= ConexaoMongo.getConnection("clientes");
		
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ativo", cli.getAtivo());

		DBCursor cursor = table.find(searchQuery);
		
		List<Cliente>list= new ArrayList<Cliente>();


		while (cursor.hasNext()) {
					
		DBObject obj= cursor.next();


		Map map= ClientesService.converteDocumentoParaMap(obj.toString());

		Set<Entry<String,String>> set =	map.entrySet();

		Iterator<Entry<String,String>> itr = set.iterator();
		Cliente clientes = new Cliente();
		  
		  while(itr.hasNext()){
			
			    	Entry<String, String> entry = itr.next();
			    	String key=entry.getKey();

			    	key = key.substring(2, key.length()-2);
			    	
		           String value=entry.getValue();
				    	
				if(key.equalsIgnoreCase("codigo")){
		    	
					clientes.setCodigo(value.substring(2,value.length()-2));
				
				} else if (key.equalsIgnoreCase("nome")){
				
					clientes.setNome(value.substring(2,value.length()-2));
				
				} else if(key.equalsIgnoreCase("cpf")){
				
					clientes.setCpf(value.substring(2,value.length()-2));
				

			    } else if(key.equalsIgnoreCase("rg")){
					
			    	clientes.setRg(value.substring(2,value.length()-2));


		  	} else if(key.equalsIgnoreCase("ativo")){
				
		    	clientes.setAtivo(value.substring(2,value.length()-2));

			}    
				
		  }
		  
		   list.add(clientes);
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