/**
 * 
 */
package org.merih.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;


/**
 * @author Merih
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket scrabbleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.merih.controllers"))
                .paths(regex("/board.*"))
                .build().apiInfo(metaData());
    }
    
    private ApiInfo metaData() {
    	
        ApiInfo apiInfo = new ApiInfo(
                "Scrabble Backend REST API",
                "Spring Boot REST API for Scrabble Backend Services",
                "1.0",
                "Terms of service",
                new Contact("Merih ilgör", "https://www.linkedin.com/in/merihilgor/", "merihilgor@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
        return apiInfo;
    }
    
}
