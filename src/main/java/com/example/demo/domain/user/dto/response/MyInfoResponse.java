package com.example.demo.domain.user.dto.response;

import com.example.demo.domain.user.entity.User;
import java.time.LocalDateTime;

public record MyInfoResponse(
    Long userId,
    String oauthId,
    String userName,
    String phoneNumber,
    LocalDateTime createdAt
) {

    public static MyInfoResponse from(User user) {
        return new MyInfoResponse(user.getId(), user.getOauthId(), user.getUserName(),
            user.getPhoneNumber(), user.getCreatedAt());
    }

}
