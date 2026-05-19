package com.example.demo.global.security.oauth;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService {

    private static final String ROLE_USER = "ROLE_USER";

    private final UserRepository userRepository;

    // 로그인 성공 후 네이버에서 받은 응답 파싱 후 User 객체 생성 또는 업데이트
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 스프링이 만들어둔 DefaultOAuth2UserService 객체의 loadUser를 호출
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        // OAuth2UserRequest yml 설정 정보, 네이버에서 주는 액세스 토큰을 담아둔 객체
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 네이버 키 : response
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.ofNaver(userNameAttributeName,
            oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        return new PrincipalDetails(
            user.getId(), user.getRole().name(), attributes.getAttributes()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        return userRepository.findByOauthId(attributes.getOauthId())
            .orElseGet(() -> userRepository.save(attributes.toEntity()));
    }
}
