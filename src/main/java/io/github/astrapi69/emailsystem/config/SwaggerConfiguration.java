package io.github.astrapi69.emailsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends AbstractSwaggerConfiguration
{

	@Bean public Docket api()
	{
		return super.api();
	}

	protected ApiInfo metaData()
	{
		return super.metaData();
	}

	@Override public String getBasePackage()
	{
		return "io.github.astrapi69.emailsystem";
	}

	@Override public String getApiInfoTitle()
	{
		return "Lottery REST API";
	}

	@Override public String getApiInfoDescription()
	{
		return "REST API for lottery and gamble";
	}

	@Override public String getApiInfoVersion()
	{
		return ApplicationConfiguration.VERSION_API_1;
	}

	@Override public String getContactName()
	{
		return "foo-gamble inc.";
	}

	@Override public String getContactUrl()
	{
		return "www.foo-gamble.com";
	}

	@Override public String getDocketPathsPathRegex()
	{
		return ApplicationConfiguration.REST_VERSION + "/.*|";
	}

}
