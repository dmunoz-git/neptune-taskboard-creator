package com.neptune.boards.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Neptune Boards API")
                .version("v1.0.0")
                .description("API endpoints that allow create a complete tasks board")
                .termsOfService("http://swagger.io/terms/")
        );
    }
}
