package rs.spaceinvade.mmdbjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MmdbJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmdbJavaApplication.class, args);
	}
	
	//FIXME: Check that following blocks are useless and delete
	
//	  public @Bean MongoClient mongoClient() {
//	      return new MongoClient("localhost");
//	  }
//
//	  public @Bean MongoTemplate mongoTemplate() {
//	      return new MongoTemplate(mongoClient(), "mmdb_java");
//	  }
}
