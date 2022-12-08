package com.xerocry.atmservice;

import com.xerocry.atmservice.security.SecurityInterceptor;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.List;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ATM API", version = "1.0", description = "API for ATM service"))
public class AtmServiceApplication {
/*    @Value("${server.ssl.key-store}")
    private Resource trustStore;

    @Value("${server.ssl.key-password}")
    private String trustStorePassword;*/

    public static void main(String[] args) {
        SpringApplication.run(AtmServiceApplication.class, args);
    }

/*    @Bean
    public RestTemplate template(RestTemplateBuilder builder) throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }*/

    @Bean
    public RestTemplate template(RestTemplateBuilder builder) {
        RestTemplate build = builder.build();
        List<ClientHttpRequestInterceptor> interceptors = build.getInterceptors();
        interceptors.add(new SecurityInterceptor());
        build.setInterceptors(interceptors);
        return build;
    }

}
