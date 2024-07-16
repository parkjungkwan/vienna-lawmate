package site.lawmate.api.service.impl;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import site.lawmate.api.domain.model.OAuth2UserInfo;
import site.lawmate.api.domain.model.PrincipalUserDetails;
import site.lawmate.api.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import site.lawmate.api.domain.vo.Registration;
import site.lawmate.api.domain.vo.Role;

@Service
@RequiredArgsConstructor
public class PrincipalOauthUserService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final WebClient webClient;

    @Override
    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        return new DefaultReactiveOAuth2UserService()
        .loadUser(userRequest)
        .log()
        .flatMap(user -> Mono.just(user.getAttributes()))
        .flatMap(attributes -> 
            Mono.just(userRequest.getClientRegistration().getClientName())
            .log()
            .flatMap(clientId -> Mono.just(Registration.valueOf(clientId.toUpperCase())))
            .flatMap(registration -> 
                Mono.just(OAuth2UserInfo.of(registration, attributes))
                // .flatMap(oauth2UserDTO -> 
                //     webClient.post()
                //     .uri("lb://user-service/auth/oauth2/" + registration.name().toLowerCase())
                //     .accept(MediaType.APPLICATION_JSON)
                //     .bodyValue(oauth2UserDTO)
                //     .retrieve()
                //     .bodyToMono(PrincipalUserDetails.class)
                // )
                .flatMap(oauth2UserInfo -> 
                    Mono.just(new PrincipalUserDetails(
                        UserModel.builder()
                            .email(oauth2UserInfo.email())
                            .name(oauth2UserInfo.name())
                            .profile(oauth2UserInfo.profile())
                            .roles(List.of(Role.ROLE_USER))
                            .build(),
                        attributes
                )))
            )
        )
        ;
    }
}