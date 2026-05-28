package com.example.demo.global.security.oauth;

import com.example.demo.domain.user.entity.User;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuthAttributes {

    private static final String ID = "id";

    private Map<String, Object> attributes;
    private String oauthId;

    // 정적 팩토리 메서드
    public static OAuthAttributes ofNaver(String userNameAttributeName,
        Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttributeName);

        return new OAuthAttributes(attributes, (String) response.get(ID));
    }

    public User toEntity() {
        return User.createUser(oauthId);
    }
}
