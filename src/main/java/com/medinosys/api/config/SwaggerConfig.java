package com.medinosys.api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Vyankatesh Deshmukh
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${config.oauth2.accessTokenUri}")
	private String accessTokenUri;

	public static final String securitySchemaOAuth2 = "oauth2schema";
	public static final String authorizationScopeGlobal = "global";
	public static final String authorizationScopeGlobalDesc = "accessEverything";

	/**
	 *
	 * @return Docket
	 */
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.medinosys.api.controller"))
               // .paths(PathSelectors.regex("/secure.*"))
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Arrays.asList(securitySchema()/*, apiKey(), apiCookieKey()*/))
                .apiInfo(apiInfo());
		
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.regex("/springjwt.*"))
//				.paths(PathSelectors.any()).build().securityContexts(Collections.singletonList(securityContext()))
//				.securitySchemes(Arrays.asList(securitySchema(), apiKey(), apiCookieKey())).apiInfo(apiInfo());

	}

	@Bean
	public SecurityScheme apiKey() {
		return new ApiKey(HttpHeaders.AUTHORIZATION, "apiKey", "header");
	}

	@Bean
	public SecurityScheme apiCookieKey() {
		return new ApiKey(HttpHeaders.COOKIE, "apiKey", "cookie");
	}

	private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = new ArrayList();
		authorizationScopeList.add(new AuthorizationScope("read", "read all"));
		authorizationScopeList.add(new AuthorizationScope("write", "access all"));

		List<GrantType> grantTypes = new ArrayList();
		GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
		grantTypes.add(passwordCredentialsGrant);

		return new OAuth("oauth2", authorizationScopeList, grantTypes);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
		authorizationScopes[0] = new AuthorizationScope("read", "read all");
		authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
		authorizationScopes[2] = new AuthorizationScope("write", "write all");

		return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
	}

	@Bean
	public SecurityConfiguration security() {
		return new SecurityConfiguration("springboot", "AB8kmzoNzl100", "", "", "Bearer access token", ApiKeyVehicle.HEADER,
				HttpHeaders.AUTHORIZATION, "");
	}

	/**
	 *
	 * @return ApiInf
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SpringBoot - Api").description("")
				.termsOfServiceUrl("https://www.google.com")
				.contact(new Contact("Developers", "https://www.google.com/", ""))
				.license("Commercial")//.licenseUrl("\"https://www.apache.org/licenses/LICENSE-2.0").version("1.0.0")
				.build();

	}

}
