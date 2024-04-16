package ai.acintyo.ezykle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication(exclude = {JacksonAutoConfiguration.class })
@PropertySource("validation-message.properties")
public class AcintyoEzykleApiApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(AcintyoEzykleApiApplication.class, args);
		
	}

}
