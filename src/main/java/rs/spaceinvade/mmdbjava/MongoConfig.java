package rs.spaceinvade.mmdbjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig{
	
	@Value("${mongodb.host}")
	private String mongodbHost;
	
	@Value("${mongodb.port}")
	private int mongodbPort;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
  public @Bean MongoDbFactory mongoDbFactory() {
	  logger.warn("******* " + mongodbHost + ":" + mongodbPort);
    return new SimpleMongoDbFactory(new MongoClient(mongodbHost, mongodbPort), "mmdb_java");
  }
}