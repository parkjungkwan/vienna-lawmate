package site.lawmate.lawyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
@EnableDiscoveryClient
@SpringBootApplication

public class LawyerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LawyerApplication.class, args);
	}

}
