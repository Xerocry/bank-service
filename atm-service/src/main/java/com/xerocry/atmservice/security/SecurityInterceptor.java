package com.xerocry.atmservice.security;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Objects;

public class SecurityInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        SecurityContext instance = SecurityContext.getInstance();
        if (Objects.nonNull(instance.getToken()))
            request.getHeaders().add("Authorization", "Bearer \n" + instance.getToken());
        return execution.execute(request, body);
    }
}