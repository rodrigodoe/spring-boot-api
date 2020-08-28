package br.com.rodrigodoe.springbootapi.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.rodrigodoe"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfor());
	}

	private ApiInfo apiInfor() {
		return new ApiInfo("RESTful API With Spring 2.1.3"
						, "Some Description about your API"
						, "V1", "Terms of Service URL"
						, new Contact("Rodrigo Carvalho", "wwww.rodrigodoe.com.br", "roda7x@gmail.com")
						, "License of API"
						, "LIcense of URL",
						Collections.emptyList());
	}
}
