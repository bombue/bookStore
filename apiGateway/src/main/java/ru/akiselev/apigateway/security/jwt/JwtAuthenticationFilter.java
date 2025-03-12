package ru.akiselev.apigateway.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private final JwtUtils jwtUtils;

    @Value("${jwt.excluded-paths}")
    private String[] excludedPaths;

//    public JwtAuthenticationFilter() {
//        super(Config.class);
//    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getPath().toString();

            // Пропускаем запросы на исключенные пути
            if (Arrays.stream(excludedPaths).anyMatch(path::startsWith)) {
                return chain.filter(exchange);
            }

            // Проверяем наличие токена в заголовке Authorization
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (!token.startsWith("Bearer ")) {
                return this.onError(exchange, "Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
            }

            token = token.substring(7); // Убираем "Bearer "

            if (!jwtUtils.validateJwtToken(token)) {
                return this.onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);

//            try {
//                // Валидируем токен
//                Claims claims = Jwts.parser()
//                        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//
//                // Добавляем claims в заголовки для дальнейшего использования
//                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                        .header("username", claims.getSubject())
//                        .header("roles", claims.get("roles", List.class).toString())
//                        .build();
//
//                return chain.filter(exchange.mutate().request(modifiedRequest).build());
//            } catch (Exception e) {
//                return this.onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
//            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config {
        // Конфигурация фильтра (если нужно)
    }
}
