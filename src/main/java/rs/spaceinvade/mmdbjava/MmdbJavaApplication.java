package rs.spaceinvade.mmdbjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

@SpringBootApplication
public class MmdbJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmdbJavaApplication.class, args);
	}
	
	  public @Bean MongoClient mongoClient() {
	      return new MongoClient("localhost");
	  }

	  public @Bean MongoTemplate mongoTemplate() {
	      return new MongoTemplate(mongoClient(), "mmdb_java");
	  }
}
