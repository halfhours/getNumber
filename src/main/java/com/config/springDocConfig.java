package com.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
@Configuration
public class springDocConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("QuHao API")
                        .description("取号")
                        .version("v1.0.0"));
    }

/*    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("brand")
                .pathsToMatch("/brand/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/admin/**")
                .build();
    }*/
}
