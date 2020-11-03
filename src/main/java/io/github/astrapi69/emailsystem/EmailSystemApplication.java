package io.github.astrapi69.emailsystem;

import io.github.astrapi69.emailsystem.config.ApplicationConfiguration;
import io.github.astrapi69.emailsystem.config.ApplicationProperties;
import io.github.astrapi69.emailsystem.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Import({ ApplicationConfiguration.class, SwaggerConfiguration.class })
@EnableConfigurationProperties({ ApplicationProperties.class })
@SpringBootApplication
public class EmailSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSystemApplication.class, args);
	}

}
