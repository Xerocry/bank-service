package com.xerocry.atmservice;

import com.xerocry.atmservice.security.SecurityInterceptor;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8300")},
        info = @Info(
                title = "ATM Service API",
                version = "v1",
                description = "Part of ATM Emulator microservice architecture, handling user interaction"
        )
)
public class AtmServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtmServiceApplication.class, args);
    }

    @Bean
    public RestTemplate template(RestTemplateBuilder builder) {
        RestTemplate build = builder.build();
        List<ClientHttpRequestInterceptor> interceptors = build.getInterceptors();
        interceptors.add(new SecurityInterceptor());
        build.setInterceptors(interceptors);
        return build;
    }

}
