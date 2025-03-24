package ru.akiselev.apigateway.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    @Value("${jwt.excluded-paths}")
    private String[] excludedPaths;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private Environment env;

    public static class Config {
        // Конфигурация фильтра (если нужно)
    }

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String jwt = authorizationHeader.replace("Bearer ", "");

            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);

//            String path = request.getPath().toString();
//
//            // Пропускаем запросы на исключенные пути
//            if (Arrays.stream(excludedPaths).anyMatch(path::startsWith)) {
//                return chain.filter(exchange);
//            }
//
//            // Проверяем наличие токена в заголовке Authorization
//            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//            if (token==null || !token.startsWith("Bearer ")) {
//                return this.onError(exchange, "Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
//            }
//
//            token = token.substring(7); // Убираем "Bearer "

//            if (!validateJwtToken(token)) {
//                return this.onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
//            }
//
//            return chain.filter(exchange);

//            try {
//                // Валидируем токен
//                Claims claims = Jwts.parser()
//                        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//
//                // Добавляем claims в заголовки для дальнейшего использования
//                // todo. Не обязательно добавлять эти claims, но мб дальше будут нужны
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
        log.error(error);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {
        Jwt<Header, Claims> parsedToken = (Jwt<Header, Claims>) Jwts.parser()
                .setSigningKey(key())
                .build()
                .parse(jwt);

        String subject = parsedToken.getPayload().getSubject();


        return subject != null && !subject.isEmpty();

//        try {
//            Jwts.parser().setSigningKey(key()).build().parse(jwt);
//            return true;
//        } catch (MalformedJwtException e) {
//            log.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            log.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            log.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            log.error("JWT claims string is empty: {}", e.getMessage());
//        }

//        return false;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
