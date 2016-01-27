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
import br.com.entities.Produto;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ProdutosService {

	public boolean insertProduto(Produto produtos) throws UnknownHostException {
		
		DBCollection table = ConexaoMongo.getConnection("produtos");
		
		BasicDBObject document = new BasicDBObject();
		document.put("descricao", produtos.getDescricao());
		document.put("preco", produtos.getPreco());
		document.put("ativo", produtos.getAtivo());
		
		if(table.insert(document) != null) {
			return true;
		}

		return false;
	}
	

	public  List<Produto> buscarProdutos(Produto prod) throws UnknownHostException {

		DBCollection table= ConexaoMongo.getConnection("produtos");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ativo", prod.getAtivo());
		
		DBCursor cursor = table.find(searchQuery);
		List<Produto>list= new ArrayList<Produto>();
		
		while (cursor.hasNext()) {
					
		DBObject obj= cursor.next();
		Map map= ProdutosService.converteDocumentoParaMap(obj.toString());
		Set<Entry<String,String>> set =	map.entrySet();
		Iterator<Entry<String,String>> itr = set.iterator();
		Produto produtos = new Produto();
		  
		  while(itr.hasNext()){
			
			    	Entry<String, String> entry = itr.next();
			    	String key=entry.getKey();
			    	key = key.substring(2,key.length()-2);
		            String value=entry.getValue();
				    	
				if(key.equalsIgnoreCase("codigo")){
					produtos.setCodigo(value.substring(2,value.length()-2));
				} else if (key.equalsIgnoreCase("descricao")){
					produtos.setDescricao(value.substring(2,value.length()-2));
				} else if(key.equalsIgnoreCase("preco")){
					produtos.setPreco(value.substring(2,value.length()-2));
			    } else if(key.equalsIgnoreCase("ativo")){
					produtos.setAtivo(value.substring(2,value.length()-2));
				}     
				
		  }
		  
		   list.add(produtos);
		}  
				return list;
		}


			public static Map<String,String> converteDocumentoParaMap (String s){
				s= s.substring(1,s.length()-1);
				String sArr[]= s.split(",");
				Map<String,String> map = new LinkedHashMap<String,String>();
				for(int i=1;i<sArr.length;i++){
					if(!sArr[i].contains("$date")) {
					String keyValue[]= sArr[i].split(":");
					map.put(keyValue[0],keyValue[1]);
					System.out.println(keyValue[0]+","+keyValue[1]);
					} else {
						String keyValue[]= sArr[i].split(":");
						map.put(keyValue[0],keyValue[2]);
					}
				}
				return map;
			}
			
	}