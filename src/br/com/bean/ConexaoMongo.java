package br.com.bean;

import java.net.UnknownHostException;
import javax.faces.bean.ManagedBean;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

@ManagedBean
public class ConexaoMongo {

	public static DBCollection getConnection(String nomeCollection) throws UnknownHostException {
			
			Mongo mongodb = new Mongo("localhost", 27017);
			DB database = mongodb.getDB("test");
			DBCollection collection = database.getCollection(nomeCollection);
			return collection;
		}

}