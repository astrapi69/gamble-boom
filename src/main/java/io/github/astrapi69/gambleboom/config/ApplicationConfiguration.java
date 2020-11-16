package io.github.astrapi69.gambleboom.config;

import java.io.File;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;

import de.alpharogroup.crypto.algorithm.KeystoreType;
import de.alpharogroup.crypto.factories.KeyStoreFactory;
import de.alpharogroup.gson.factory.GsonFactory;
import de.alpharogroup.gson.strategy.GenericExclusionStrategy;
import de.alpharogroup.lang.ClassExtensions;
import de.alpharogroup.sign.JsonSigner;
import de.alpharogroup.sign.JsonVerifier;
import de.alpharogroup.sign.SignatureBean;
import de.alpharogroup.sign.VerifyBean;
import de.alpharogroup.sign.annotation.SignatureExclude;
import de.alpharogroup.throwable.RuntimeExceptionDecorator;
import io.github.astrapi69.gambleboom.jpa.entity.Draws;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Configuration
@ComponentScan(basePackages = { "io.github.astrapi69.gambleboom",
		"io.github.astrapi69.gambleboom.service", "io.github.astrapi69.gambleboom.jpa.entity" })
@EntityScan(basePackages = { "io.github.astrapi69.gambleboom.jpa.entity" })
@EnableJpaRepositories(basePackages = { "io.github.astrapi69.gambleboom.jpa.repository" })
@EnableTransactionManagement
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationConfiguration implements WebMvcConfigurer
{

	public static final String VERSION_API_1 = "v1";
	public static final String REST_VERSION = "/" + VERSION_API_1;

	ApplicationProperties applicationProperties;

	@SuppressWarnings("unused")
	Environment env;

	public static ObjectMapper initialize(final @NonNull ObjectMapper objectMapper)
	{
		SimpleModule module;
		JavaTimeModule javaTimeModule;
		SimpleAbstractTypeResolver resolver;

		module = new SimpleModule("lottery-app", Version.unknownVersion());
		resolver = new SimpleAbstractTypeResolver();
		module.setAbstractTypes(resolver);
		objectMapper.registerModule(module);

		javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		return objectMapper;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedOrigins("*");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
	{
		converters.add(createXmlHttpMessageConverter());
		converters.add(newMappingJackson2HttpMessageConverter());
	}

	private MappingJackson2HttpMessageConverter newMappingJackson2HttpMessageConverter()
	{
		return new MappingJackson2HttpMessageConverter();
	}

	private HttpMessageConverter<Object> createXmlHttpMessageConverter()
	{
		MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();

		XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
		xmlConverter.setMarshaller(xstreamMarshaller);
		xmlConverter.setUnmarshaller(xstreamMarshaller);

		return xmlConverter;
	}

	@Bean
	public MessageSource messageSource()
	{
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages/errors");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public ObjectMapper objectMapper()
	{
		return initialize(new ObjectMapper());
	}

	@Bean
	public KeyStore keyStore()
	{
		String keystoreFilename = applicationProperties.getKeystoreFilename();
		File keystoreFile = RuntimeExceptionDecorator
			.decorate(() -> ClassExtensions.getResourceAsFile(keystoreFilename));
		return RuntimeExceptionDecorator
			.decorate(() -> KeyStoreFactory.loadKeyStore(keystoreFile, KeystoreType.JKS.name(),
				applicationProperties.getKeystorePassword()));
	}

	@Bean
	public SignatureBean signatureBean()
	{
		String pkAlias = applicationProperties.getPkAlias();
		char[] chars = applicationProperties.getKeystorePassword().toCharArray();
		KeyStore keyStore = keyStore();
		PrivateKey privateKey = RuntimeExceptionDecorator
			.decorate(() -> (PrivateKey)keyStore.getKey(pkAlias, chars));
		String signatureAlgorithm = applicationProperties.getSignatureAlgorithm();
		return SignatureBean.builder().privateKey(privateKey).signatureAlgorithm(signatureAlgorithm)
			.build();
	}

	@Bean
	public VerifyBean verifyBean()
	{
		String pkAlias = applicationProperties.getPkAlias();
		char[] chars = applicationProperties.getKeystorePassword().toCharArray();
		String signatureAlgorithm = applicationProperties.getSignatureAlgorithm();
		KeyStore keyStore = keyStore();
		Certificate certificate = RuntimeExceptionDecorator
			.decorate(() -> keyStore.getCertificate(pkAlias));
		return VerifyBean.builder().certificate(certificate).signatureAlgorithm(signatureAlgorithm)
			.build();
	}

	@Bean
	public Gson gson()
	{
		return GsonFactory.newGsonBuilder(new GenericExclusionStrategy<>(SignatureExclude.class),
			"dd-MM-yyyy hh:mm:ss");
	}

	@Bean
	public JsonSigner<Draws> drawsJsonSigner()
	{
		return new JsonSigner<>(signatureBean(), gson());
	}


	@Bean
	public JsonVerifier<Draws> drawsJsonVerifier()
	{
		return new JsonVerifier<>(verifyBean(), gson());
	}

}
