package harry.mongodb;

import static com.mongodb.client.model.Filters.eq;

import java.util.Arrays;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * 
 * @author harry
 *
 */
public class TestMongodb {
	@Test
	public void testConnction() {
		try(MongoClient mongoClient = new MongoClient("192.168.0.116", 27017);){
			for (String dbName : mongoClient.listDatabaseNames()) {
				System.out.println(dbName);
			}
		}
	}

	@Test
	public void testCreate() {
		try(MongoClient mongoClient = new MongoClient("192.168.0.116", 27017);){
			MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("test");
			Document document = new Document("name", "MongoDB").append("type", "db").append("count", 1)
					.append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
					.append("info", new Document("x", 203).append("y", 102));
			collection.insertOne(document);
		}
	}

	@Test
	public void testQuery() {
		try(MongoClient mongoClient = new MongoClient("192.168.0.116", 27017);){
			MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("test");
			System.out.println(collection.count());
			for (Document document : collection.find()) {
				System.out.println(document.toJson());
			}
			
			System.out.println("----------------------------------");
			Document first = collection.find(eq("name","MongoDB")).first();
			System.out.println(first.toJson());
		}
	}
	
	@Test
	public void testUpdate(){
		try(MongoClient mongoClient = new MongoClient("192.168.0.116", 27017);){
			MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("test");
			collection.updateOne(eq("name", "test"), new Document("$set", new Document("name", "redis")));
		}
	}
	
	@Test
	public void testDelete(){
		try(MongoClient mongoClient = new MongoClient("192.168.0.116", 27017);){
			MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("test");
			collection.deleteOne(eq("name", "redis"));
		}
	}
}
