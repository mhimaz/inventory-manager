package com.inventory.manager.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
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

    /**
     * Every Docket bean is picked up by the swagger-mvc framework - allowing for multiple swagger groups i.e. same code
     * base multiple swagger resource listings.
     */
    @Bean
    public Docket api() {

        // Filtering API
        return new Docket(DocumentationType.SWAGGER_2).groupName("inventory-manager").select()
                .apis(RequestHandlerSelectors.basePackage("com.inventory.manager.controller"))
                // can use any(), none(), regex(), or ant()
                // .paths(PathSelectors.ant("/products/*"))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("Inventory Manager")
                .description("Inventory Manager API Documentation")
                .termsOfServiceUrl("http://localhost:8080/inventory-manager/terms")
                .contact(
                        new Contact("Inventory Manager", "http://localhost:8080/inventory-manager/contact",
                                "mhimaz@gmail.com")).license("Private License")
                .licenseUrl("http://localhost:8080/inventory-manager/terms").version("1.0").build();
    }

    // @Bean
    // public Docket adminApi() {
    //
    // return new Docket(DocumentationType.SWAGGER_2).groupName("placepass-admin-api").select()
    // .apis(RequestHandlerSelectors.basePackage("com.placepass.booking.admincontroller"))
    // .paths(PathSelectors.any()).build().apiInfo(adminApiInfo());
    //
    // }
    //
    // private ApiInfo adminApiInfo() {
    //
    // return new ApiInfoBuilder().title("Booking Service Admin API")
    // .description("Booking Service Admin API Documentation")
    // .termsOfServiceUrl("https://www.placepass.com/terms")
    // .contact(new Contact("Place Pass", "https://www.placepass.com/contact", "developer@placepass.com"))
    // .license("Private License").licenseUrl("https://www.placepass.com/terms").version("1.0").build();
    // }

}
