package ai.acintyo.ezykle.config;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

@Configuration
public class Configure {

	  @Bean
	    public ReloadableResourceBundleMessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasename("classpath:validation-message");
	        messageSource.setDefaultEncoding("UTF-8");
	        messageSource.setCacheSeconds(10); // Reload messages every 10 seconds
	        return messageSource;
	    }
	  @Bean 
	  public Jackson2ObjectMapperBuilder createJackson2ObjectMapperBuilder()
	  {
		  return new Jackson2ObjectMapperBuilder().serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
				  .deserializers(new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
	  }
}
