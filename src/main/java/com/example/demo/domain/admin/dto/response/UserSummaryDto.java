package com.example.demo.domain.admin.dto.response;

import com.example.demo.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSummaryDto {
    private final LocalDateTime createdAt;
    private final String userName;
    private final String email;
    private final Boolean applied;

    public static UserSummaryDto from(User user){
        return UserSummaryDto.builder()
                   .createdAt(user.getCreatedAt())
                   .userName(user.getUserName())
                   .email(user.getEmail())
                   .applied(user.getApplied())
                   .build();
    }
}
