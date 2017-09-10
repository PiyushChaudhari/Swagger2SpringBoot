package com.rest.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	ModelRef errorModel = new ModelRef("RestApiExceptionModel");
	List<ResponseMessage> responseMessages = Arrays.asList(
			new ResponseMessageBuilder().code(401).message("Unauthorized!!!").responseModel(errorModel).build(),
			new ResponseMessageBuilder().code(403).message("Forbidden!!!").responseModel(errorModel).build(),
			new ResponseMessageBuilder().code(404).message("NotFound!!!").responseModel(errorModel).build(),
			new ResponseMessageBuilder().code(405).message("Method Not Allowed!!!").responseModel(errorModel).build(),
			new ResponseMessageBuilder().code(500).message("Internal Error!!!").responseModel(errorModel).build());

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.rest.controller")).paths(PathSelectors.ant("/api/**"))
				.build().apiInfo(apiInfo()).useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.POST, responseMessages)
				.globalResponseMessage(RequestMethod.PUT, responseMessages)
				.globalResponseMessage(RequestMethod.GET, responseMessages)
				.globalResponseMessage(RequestMethod.DELETE, responseMessages);

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot REST API with Swagger")
				.description("Spring Boot REST API with Swagger")
				.termsOfServiceUrl("http://localhost:8080/Swagger2SpringBoot/swagger-ui.html")
				// .contact("Niklas Heidloff")
				// .license("Apache License Version 2.0")
				// .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
				// .version("2.0")
				.build();
	}
}
