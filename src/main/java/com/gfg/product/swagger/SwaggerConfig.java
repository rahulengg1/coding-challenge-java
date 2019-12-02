package com.gfg.product.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiV1() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .apiInfo(apiInfo("1.0.0"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gfg.product.controller"))
                .paths(PathSelectors.regex("/((?!error).)*"))
                .paths(PathSelectors.regex("/api/v1.*"))
                .build();
        docket.useDefaultResponseMessages(false)
        .directModelSubstitute(Object.class, java.lang.Void.class);;

        return docket;
    }

    private ApiInfo apiInfo(String vesion) {
        return new ApiInfoBuilder()
                .title("Product Rest API")
                .description("REST API for Product and Seller")
                .version(vesion)
                .build();
    }
   
    @Bean
    public Docket apiV2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("V2")
                .apiInfo(apiInfo("2.0.0"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gfg.product.controller"))
                .paths(PathSelectors.regex("/api/v2.*"))
                .paths(PathSelectors.regex("/((?!error).)*"))
                .build();
         docket.useDefaultResponseMessages(false)
        .directModelSubstitute(Object.class, java.lang.Void.class);;

        return docket;
    }

}
