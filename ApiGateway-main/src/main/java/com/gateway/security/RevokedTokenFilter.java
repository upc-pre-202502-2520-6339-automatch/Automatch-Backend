
package com.gateway.security;
/*
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;

@Component
public class RevokedTokenFilter implements GlobalFilter, Ordered {

    private final ReactiveStringRedisTemplate redis;
    private final String expectedAlg; // HS384

    public RevokedTokenFilter(ReactiveStringRedisTemplate redis,
                              @Value("${security.jwt.alg:HS384}") String expectedAlg) {
        this.redis = redis;
        this.expectedAlg = expectedAlg;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) return chain.filter(exchange);

        String token = auth.substring(7);
        // leer header+payload sin verificar firma (rápido) solo para extraer jti y alg
        try {
            String[] parts = token.split("\\.");
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            if (!headerJson.contains("\"alg\":\"" + expectedAlg + "\"")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String jti = extract(payloadJson, "\"jti\":\"", "\"");
            if (jti == null || jti.isBlank()) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return redis.opsForValue().get("revoked:" + jti)
                    .flatMap(val -> {
                        if (val != null) {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                        return chain.filter(exchange);
                    })
                    .switchIfEmpty(chain.filter(exchange));
        } catch (Exception e) {
            // si el token está malformado -> 401
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private String extract(String json, String prefix, String end) {
        int i = json.indexOf(prefix);
        if (i < 0) return null;
        int s = i + prefix.length();
        int e = json.indexOf(end, s);
        return e > s ? json.substring(s, e) : null;
    }

    @Override
    public int getOrder() { return -100; } // antes de ruteo
}
*/