package site.lawmate.api.service;

import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import site.lawmate.api.domain.dto.LoginDto;

public interface AuthService {
    Mono<ServerResponse> localLogin(LoginDto dto);
    Mono<ServerResponse> refresh(String refreshToken);
    Mono<ServerResponse> logout(String refreshToken);
}