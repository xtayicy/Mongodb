package harry.mongodb;

import static com.mongodb.client.model.Filters.eq;

import java.util.Arrays;

import org.bson.Document;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author harry
 *
 */
public class TestMongodb {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestMongodb.class);
	
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 27017;
	
	@Test
	public void testConnction() {
		try(MongoClient mongoClient = new MongoClient(HOST, PORT);){
			for (String dbName : mongoClient.listDatabaseNames()) {
				LOGGER.info(dbName);
			}
		}
	}

	@Test
	public void testCreate() {
		try(MongoClient mongoClient = new MongoClient(HOST, PORT);){
			MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("test");
			Document document = new Document("name", "MongoDB").append("type", "db").append("count", 1)
					.append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
					.append("info", new Document("x", 203).append("y", 102));
			collection.insertOne(document);
		}
	}

	@Test
	public void testQuery() {
		try(MongoClient mongoClient = new MongoClient(HOST, PORT);){
			MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("customer");
			System.out.println(collection.count());
			for (Document document : collection.find()) {
				LOGGER.info(document.toJson());
			}
			
			/*
			LOGGER.info("----------------------------------");
			Document document = collection.find(eq("name","MongoDB")).first();
			LOGGER.info(document.toJson());
			*/
		}
	}
	
	@Test
	public void testUpdate(){
		try(MongoClient mongoClient = new MongoClient(HOST, PORT);){
			MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("test");
			collection.updateOne(eq("name", "test"), new Document("$set", new Document("name", "redis")));
		}
	}
	
	@Test
	public void testDelete(){
		try(MongoClient mongoClient = new MongoClient(HOST, PORT);){
			MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("test");
			collection.deleteOne(eq("name", "redis"));
		}
	}
}
