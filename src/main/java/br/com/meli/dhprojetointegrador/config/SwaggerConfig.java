package br.com.meli.dhprojetointegrador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
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
                    .apis(RequestHandlerSelectors.basePackage("br.com.meli.dhprojetointegrador.controller"))
                    .paths(PathSelectors.any())
                    .build()
                    .apiInfo(apiInfo());
        }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .license("Common Licenses")
                .title("Projeto Integrador")
                .description("Bootcamp - Mercado Livre")
                .version("1.0.0")
                .contact(new Contact("JackPot Squad", "https://davidalexandrefernandes.atlassian.net/l/c/Bed22y32", "mercadolivre@mercadolivre.com"))
                .build();
    }


}
