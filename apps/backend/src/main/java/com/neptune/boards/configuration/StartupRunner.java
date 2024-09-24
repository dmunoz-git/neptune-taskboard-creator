package com.neptune.boards.configuration;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(String... args) throws Exception {
        String apiUrl = "http://localhost:" + serverPort + "/api"; // URL base de la API
        String swaggerUrl = "http://localhost:" + serverPort + "/doc/swagger-ui/index.html"; // URL de Swagger

        System.out.println("**********************************************************");
        System.out.println("API Documentation available at: " + swaggerUrl);
        System.out.println("API Base URL: " + apiUrl);
        System.out.println("**********************************************************");
    }
}
