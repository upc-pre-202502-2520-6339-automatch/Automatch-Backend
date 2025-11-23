package com.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * Created by Alex Avila Asto - A.K.A (Ryzeon)
 * Project: inncontrol-backend
 * Date: 7/1/25 @ 15:40
 */


@Configuration
@Slf4j
public class WebFluxConfiguration {

    @Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> {
            var req = exchange.getRequest();
            var res = exchange.getResponse();
            var headers = res.getHeaders();

            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
            headers.add("Access-Control-Allow-Headers", "*");
            headers.add("Access-Control-Max-Age", "3600");

            // Preflight
            if ("OPTIONS".equalsIgnoreCase(req.getMethod().name())) {
                res.setStatusCode(HttpStatus.OK);
                return res.setComplete();
            }
            return chain.filter(exchange);
        };
    }


    @Bean
    public WebFilter logRequestFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            String method = exchange.getRequest().getMethod().name();
            String path = exchange.getRequest().getPath().toString();
            log.info("📌 Request received: [{}] {}", method, path);
            return chain.filter(exchange).doOnSuccess(done ->
                    log.info("✅ Response sent for: [{}] {}", method, path)
            );
        };
    }

}