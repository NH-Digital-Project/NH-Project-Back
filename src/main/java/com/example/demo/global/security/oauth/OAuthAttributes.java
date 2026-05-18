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
    private static final String NAME = "name";

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String naverId;
    private String naverName;
    // Todo 이메일 추가 필요

    // 정적 팩토리 메서드
    public static OAuthAttributes ofNaver(String userNameAttributeName,
        Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttributeName);

        return new OAuthAttributes(attributes, userNameAttributeName, (String) response.get(ID),
            (String) response.get(NAME));
    }

    public User toEntity() {
        return User.create(naverId, naverName);
    }
}
