package io.github.astrapi69.gambleboom;

import io.github.astrapi69.gambleboom.config.ApplicationProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableConfigurationProperties
public class TestConfiguration
{

	@Bean
	@ConfigurationProperties(prefix = "app")
	ApplicationProperties applicationProperties(){
		ApplicationProperties applicationProperties = new ApplicationProperties();
		applicationProperties.setKeystoreFilename("keystore.jks");
		applicationProperties.setKeystorePassword("keystore-pw");
		applicationProperties.setPkAlias("app-priv-key");
		applicationProperties.setSignatureAlgorithm("SHA256withRSA");
		return applicationProperties;
	}
}
