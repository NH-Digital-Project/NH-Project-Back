package com.example.demo.domain.admin.dto.response;

import com.example.demo.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSummaryDto {
    private final Long userId;
    private final String oauthId;
    private final LocalDateTime createdAt;
    private final String userName;
    private final String phoneNumber;
    private final Boolean applied;

    public static UserSummaryDto from(User user, Boolean applied) {
        return UserSummaryDto.builder()
                .userId(user.getId())
                .oauthId(user.getOauthId())
                .createdAt(user.getCreatedAt())
                .userName(user.getUserName())
                .phoneNumber(user.getPhoneNumber())
                .applied(applied)
                .build();
    }
}
