package io.github.astrapi69.gambleboom;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.github.astrapi69.gambleboom.config.ApplicationProperties;

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
