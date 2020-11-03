package io.github.astrapi69.emailsystem.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationProperties
{

	String dbHost;
	String dbName;
	int dbPort;
	String dbUrlPrefix;
	String dbUsername;
	String dbPassword;
	String dir;
	String name;
	String stripePublicKey;
	String stripeSecretKey;
	String jwtSecret;
	List<String> publicPathPatterns = new ArrayList<>();
	List<String> signinPathPatterns = new ArrayList<>();
	List<String> ignorePathPatterns = new ArrayList<>();

}
