package site.lawmate.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import site.lawmate.api.domain.dto.LoginDto;
import site.lawmate.api.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/login/local")
    public Mono<ServerResponse> login(@RequestBody LoginDto dto) {
        return authService.localLogin(dto);
    }

    @PostMapping("/refresh")
    public Mono<ServerResponse> refresh(@RequestHeader(name = "Authorization") String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @PostMapping("/logout")
    public Mono<ServerResponse> logout(@RequestHeader(name = "Authorization") String refreshToken) {
        return authService.logout(refreshToken);
    }    
}